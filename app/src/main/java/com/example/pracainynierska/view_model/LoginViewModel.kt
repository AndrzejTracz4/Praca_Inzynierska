package com.example.pracainynierska.view_model

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.handler.authorization.PlayerAuthorizationHandler
import com.example.pracainynierska.model.Task
import com.example.pracainynierska.model.User
import com.example.pracainynierska.repository.UserRepository
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {

    // Zmienna przechowująca wartość nazwy użytkownika
    var username by mutableStateOf("")
        private set

    // Zmienna przechowująca wartość hasła użytkownika
    var password by mutableStateOf("")
        private set

    // Zmienna przechowująca wartość potwierdzenia hasła użytkownika
    var confirmPassword by mutableStateOf("")
        private set

    // Zmienna przechowująca wartość email użytkownika
    var email by mutableStateOf("")
        private set

    // Flaga określająca, czy logowanie się powiodło
    var loginSuccess by mutableStateOf(false)
        private set

    // Zmienna przechowująca komunikat o błędzie nazwy użytkownika
    var usernameErrorMessage by mutableStateOf<String?>(null)
        private set

    // Zmienna przechowująca komunikat o błędzie hasła
    var passwordErrorMessage by mutableStateOf<String?>(null)
        private set

    // Zmienna przechowująca komunikat o błędzie potwierdzenia hasła
    var confirmPasswordErrorMessage by mutableStateOf<String?>(null)
        private set

    // Zmienna przechowująca komunikat o błędzie emaila
    var emailErrorMessage by mutableStateOf<String?>(null)
        private set

    // Zmienna przechowująca "username" aktualnie zalogowanego użytkownika
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user


    // Ogólna zmienna przechowująca komunikaty o błędach
    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isResetCodeSent by mutableStateOf<Boolean?>(null)

    // Zmienna przechowująca nowe hasło
    var newPassword by mutableStateOf("")
        private set

    // Zmienna przechowująca potwierdzenie nowego hasła
    var confirmNewPassword by mutableStateOf("")
        private set

    // Zmienna przechowująca komunikat o błędzie nowego hasła
    var newPasswordErrorMessage by mutableStateOf<String?>(null)
        private set

    // Zmienna przechowująca komunikat o błędzie potwierdzenia nowego hasła
    var confirmNewPasswordErrorMessage by mutableStateOf<String?>(null)
        private set

    // Funkcja do zmiany nowego hasła i walidacji hasła
    fun onNewPasswordChange(newPassword: String) {
        this.newPassword = newPassword
        newPasswordErrorMessage = validatePassword(newPassword)
    }

    // Funkcja do zmiany potwierdzenia nowego hasła i walidacji, czy hasła się zgadzają
    fun onConfirmNewPasswordChange(newConfirmPassword: String) {
        confirmNewPassword = newConfirmPassword
        validateNewPasswords()
    }

    // Funkcja do walidacji zgodności nowych haseł
    private fun validateNewPasswords() {
        confirmNewPasswordErrorMessage = if (newPassword != confirmNewPassword) {
            "Passwords do not match!"
        } else {
            null
        }
    }

    fun changePasswordByEmail(email: String, newPassword: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val hashedNewPassword = hashPassword(newPassword)

                val existingUser = userRepository.getUserByEmail(email) // Znajdź użytkownika po e-mailu
                if (existingUser != null) {
                    existingUser.password = hashedNewPassword // Zaktualizuj hasło
                    userRepository.upsertUser(existingUser) // Zapisz zaktualizowanego użytkownika
                    onSuccess()
                } else {
                    onError("User not found.")
                }
            } catch (e: Exception) {
                onError("Failed to change password: ${e.message}")
            }
        }
    }


    // Funkcja do zmiany nazwy użytkownika i walidacji tej nazwy
    fun onUsernameChange(newUsername: String) {
        username = newUsername
        usernameErrorMessage = validateUsername(newUsername)
    }

    // Funkcja do zmiany hasła i walidacji hasła
    fun onPasswordChange(newPassword: String) {
        password = newPassword
        passwordErrorMessage = validatePassword(newPassword)
    }

    // Funkcja do zmiany emaila i walidacji emaila
    fun onEmailChange(newEmail: String) {
        email = newEmail
        emailErrorMessage = validateEmail(newEmail)
    }

    // Funkcja do zmiany potwierdzenia hasła i walidacji, czy hasła się zgadzają
    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
        validatePasswords()
    }

    // Funkcja do walidacji nazwy użytkownika
    private fun validateUsername(username: String): String? {
        return if (username.isBlank()) "Username cannot be empty!" else null
    }

    // Funkcja do walidacji hasła
    private fun validatePassword(password: String): String? {
        return if (password.isBlank()) "Password cannot be empty" else null
    }

    // Funkcja do walidacji zgodności haseł
    private fun validatePasswords() {
        confirmPasswordErrorMessage = if (password != confirmPassword) {
            "Passwords do not match!"
        }else {
            null
        }
    }

    // Funkcja do walidacji emaila
    private fun validateEmail(email: String): String? {
        return if (email.isBlank()) "Email cannot be empty" else null
    }

    fun login(onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val player = PlayerAuthorizationHandler().authorize(email, password)
            if (player != null) {
                Log.d("LoginViewModel", "Player: $player")
                loginSuccess = true
                onLoginResult(true)
            } else {
                Log.d("LoginViewModel", "Player is null")
                emailErrorMessage = "Invalid username or password"
                passwordErrorMessage = "Invalid username or password"
                onLoginResult(false)
            }
        }
    }

    // Funkcja do hashowania hasła
    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    // Funkcja do resetowania hasła
    fun forgotPassword(email: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val existingUser = userRepository.getUserByEmail(email)

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    onError("Invalid email address format")
                    return@launch
                }

                if (existingUser == null) {
                    onError("Email not found in the database.")
                    return@launch
                }

                onSuccess()
            } catch (e: Exception) {
                onError("Wystąpił błąd: ${e.message}")
            }
        }
    }


    // Metoda do aktualizacji ścieżki zdjęcia użytkownika
    fun updateUserPhotoPath(photoPath: String) {
        // Obserwacja userId, aby uzyskać aktualne ID zalogowanego użytkownika
        val currentUserId = _user.value?.id
        Log.d("updateUserPhotoPath", "UserId: $currentUserId")

        if (currentUserId != null) {
            viewModelScope.launch {
                try {
                    // Aktualizacja ścieżki zdjęcia użytkownika
                    userRepository.updateUserPhotoPath(currentUserId, photoPath)
                } catch (_: Exception) { }
            }
        }
    }

    //todo obsługa zadań powinna być osobnym view modelem
    //Obsługa zadań
    private val _tasks = MutableLiveData<List<Task>>(emptyList())
    val tasks: LiveData<List<Task>> = _tasks

    fun addTask(task: Task) {
        _tasks.value = _tasks.value?.plus(task)
    }

    fun completeTask(taskId: Int) {
        _tasks.value = _tasks.value?.map { if (it.id == taskId) it.copy(status = "Completed") else it }
    }

    fun editTask(task: Task) {
        _tasks.value = _tasks.value?.map { if (it.id == task.id) task else it }
    }

    fun deleteTask(taskId: Int) {
        _tasks.value = _tasks.value?.filter { it.id != taskId }
    }

    fun getTasksForDate(selectedDate: String): List<Task>? {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val selectedDateParsed = dateFormat.parse(selectedDate) ?: return emptyList()

        // Format daty dla startDate i endDate
        val taskDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())

        return tasks.value?.filter { task ->
            val taskStartDate = taskDateFormat.parse(task.startDate)
            val taskEndDate = taskDateFormat.parse(task.endDate)

            // Sprawdzenie czy zadanie jest w zakresie wybranej daty
            taskStartDate != null && taskEndDate != null &&
                    selectedDateParsed.isSameDay(taskStartDate) ||
                    selectedDateParsed.isSameDay(taskEndDate) ||
                    (selectedDateParsed.after(taskStartDate) && selectedDateParsed.before(taskEndDate))
        }
    }

    // Porównanie dat bez godzin
    //todo powinno być w osobnym serwisie (np sameDateChecker)
    private fun Date.isSameDay(other: Date): Boolean {
        val calendar1 = Calendar.getInstance().apply { time = this@isSameDay }
        val calendar2 = Calendar.getInstance().apply { time = other }
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
    }

}