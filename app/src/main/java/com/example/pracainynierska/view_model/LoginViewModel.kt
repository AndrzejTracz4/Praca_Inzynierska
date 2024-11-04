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
import com.example.pracainynierska.model.Task
import com.example.pracainynierska.model.User
import com.example.pracainynierska.repository.UserRepository
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

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

    // Funkcja do zmiany hasła po zalogowaniu
    fun changePassword(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (newPassword.isBlank() || confirmNewPassword.isBlank()) {
            onError("All fields must be filled!")
            return
        }

        if (newPasswordErrorMessage != null || confirmNewPasswordErrorMessage != null) {
            onError("Please fix the errors before submitting.")
            return
        }

        viewModelScope.launch {
            try {
                val hashedNewPassword = hashPassword(newPassword)

                val currentUser = _user.value
                if (currentUser != null) {
                    currentUser.password = hashedNewPassword // Zaktualizuj hasło
                    userRepository.upsertUser(currentUser) // Zapisz zaktualizowanego użytkownika
                    onSuccess()
                } else {
                    onError("User not found.")
                }
            } catch (e: Exception) {
                onError("Failed to change password: ${e.message}")
            }
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

    // Funkcja do logowania użytkownika
//    fun login(navController: NavController) {
//        viewModelScope.launch {
//            usernameErrorMessage = null
//            passwordErrorMessage = null
//
//            val hashedInputPassword = hashPassword(password)
//
//            val user = userRepository.getUser(username, hashedInputPassword)
//
//            viewModelScope.launch {
//                try {
//                    val apiUser = UserApi("http://127.0.0.1")
//                    val testUser = withContext(Dispatchers.IO) {
//                        apiUser.apiUsersIdGet("1")
//                    }
//                    Log.d("LoginViewModel", "testApiUser: $testUser")
//                } catch (e: Exception) {
//                    Log.e("LoginViewModel", "Error fetching user: ${e.message}")
//                }
//            }
//
//            if (user != null) {
//                val userUUID = user.userUUID
//                // Pobierz dane użytkownika
//                loginSuccess = true
//                fetchUser(userUUID)
//                // Przekazanie nazwy użytkownika do HomepageView
//                navController.navigate("HomepageView/$userUUID")
//            } else {
//                usernameErrorMessage = "Invalid username or password"
//                passwordErrorMessage = "Invalid username or password"
//            }
//        }
//    }

    fun login(onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            usernameErrorMessage = null
            passwordErrorMessage = null

            val hashedInputPassword = hashPassword(password)
            val user = userRepository.getUser(username, hashedInputPassword)

            if (user != null) {
                val userUUID = user.userUUID
                // Pobranie danych użytkownika
                loginSuccess = true
                fetchUser(userUUID)
                onLoginResult(true) // Zwrócenie informacji o udanym logowaniu
            } else {
                usernameErrorMessage = "Invalid username or password"
                passwordErrorMessage = "Invalid username or password"
                onLoginResult(false) // Logowanie nieudane
            }
        }
    }



    // Funkcja do rejestracji użytkownika
    fun registerUser(onSuccess: () -> Unit, onError: (String) -> Unit) {
        // Sprawdzenie, czy którekolwiek pole jest puste
        if (username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            onError("All fields must be filled!")
            return
        }

        // Sprawdzenie, czy hasła pasują do siebie
        if (password != confirmPassword) {
            onError("Passwords do not match")
            return
        }

        // Sprawdzenie, czy email jest prawidłowy
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            onError("Invalid email format")
            return
        }

        viewModelScope.launch {
            try {
                // Sprawdzenie, czy użytkownik z podanym username lub email już istnieje
                val existingUserByUsername = userRepository.getUserByUsername(username)
                val existingUserByEmail = userRepository.getUserByEmail(email)

                // Sprawdzenie czy Username jest już w bazie
                if (existingUserByUsername != null) {
                    onError("Username is already taken")
                    return@launch
                }

                // Sprawdzenie czy Email jest już w bazie
                if (existingUserByEmail != null) {
                    onError("Email is already registered")
                    return@launch
                }

                // Hashowanie hasła przed zapisaniem do bazy
                val hashedPassword = hashPassword(password)

                val userUUID = generateRandomUserUUID()

                // Tworzenie nowego użytkownika i zapisanie go do bazy
                val user = User(
                    username = username,
                    email = email,
                    password = hashedPassword,
                    userPhotoPath = "app/src/main/res/raw/user_photo_1.json",
                    level = 1, experience = 0f,
                    userUUID = userUUID,
                    determination = 0f,
                    physical_fitness = 0f,
                    intelligence = 0f,
                    knowledge = 0f)
                userRepository.upsertUser(user)
                onSuccess()
            } catch (e: Exception) {
                onError("Registration failed: ${e.message}")
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

//                val newPassword = generateRandomPassword()

//                val hashedForgotPassword = hashPassword(newPassword)

//                existingUser.password = hashedForgotPassword
//                userRepository.upsertUser(existingUser)

//                sendEmailWithNewPassword(email, newPassword)

                onSuccess()
            } catch (e: Exception) {
                onError("Wystąpił błąd: ${e.message}")
            }
        }
    }

    //Funkcja generuje UUID użytkownika
    private fun generateRandomUserUUID(): String {
        return UUID.randomUUID().toString().take(12)
    }

    //Pobiera dane zalogowanego użytkownika z bazy danych na podstawie podanego username,
    //viewModelScope to korutyna która działa asynchronicznie, czyli po prostu działa w tle
    fun fetchUser(userUUID: String) {
        userRepository.getUserByUserUUIDLiveData(userUUID).observeForever { fetchedUser ->
            _user.value = fetchedUser
            Log.d("fetchUser", "fetchUser: ${_user.value}")
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
    private fun Date.isSameDay(other: Date): Boolean {
        val calendar1 = Calendar.getInstance().apply { time = this@isSameDay }
        val calendar2 = Calendar.getInstance().apply { time = other }
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
    }

}