package com.example.pracainynierska.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.handler.authorization.AuthorizationHandlerInterface
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.model.Task
import com.example.pracainynierska.model.User
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class LoginViewModel(
    pc: PlayerContextInterface,
    private val playerAuthorizationHandler: AuthorizationHandlerInterface
) : AbstractViewModel(pc) {

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var loginSuccess by mutableStateOf(false)
        private set

    var usernameErrorMessage by mutableStateOf<String?>(null)
        private set

    var passwordErrorMessage by mutableStateOf<String?>(null)
        private set

    var confirmPasswordErrorMessage by mutableStateOf<String?>(null)
        private set

    var emailErrorMessage by mutableStateOf<String?>(null)
        private set

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user


    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isResetCodeSent by mutableStateOf<Boolean?>(null)

    var newPassword by mutableStateOf("")
        private set

    var confirmNewPassword by mutableStateOf("")
        private set

    var newPasswordErrorMessage by mutableStateOf<String?>(null)
        private set

    var confirmNewPasswordErrorMessage by mutableStateOf<String?>(null)
        private set

    fun onNewPasswordChange(newPassword: String) {
        this.newPassword = newPassword
        newPasswordErrorMessage = validatePassword(newPassword)
    }

    fun onConfirmNewPasswordChange(newConfirmPassword: String) {
        confirmNewPassword = newConfirmPassword
        validateNewPasswords()
    }

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

                throw NotImplementedError("Not implemented")
            } catch (e: Exception) {
                onError("Failed to change password: ${e.message}")
            }
        }
    }


    fun onUsernameChange(newUsername: String) {
        username = newUsername
        usernameErrorMessage = validateUsername(newUsername)
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        passwordErrorMessage = validatePassword(newPassword)
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
        emailErrorMessage = validateEmail(newEmail)
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
        validatePasswords()
    }

    private fun validateUsername(username: String): String? {
        return if (username.isBlank()) "Username cannot be empty!" else null
    }

    private fun validatePassword(password: String): String? {
        return if (password.isBlank()) "Password cannot be empty" else null
    }

    private fun validatePasswords() {
        confirmPasswordErrorMessage = if (password != confirmPassword) {
            "Passwords do not match!"
        } else {
            null
        }
    }

    private fun validateEmail(email: String): String? {
        return if (email.isBlank()) "Email cannot be empty" else null
    }

    fun login(onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val player = playerAuthorizationHandler.authorize(email, password)
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

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    fun forgotPassword(email: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                throw NotImplementedError("Not implemented")
            } catch (e: Exception) {
                onError("Wystąpił błąd: ${e.message}")
            }
        }
    }


    fun updateUserPhotoPath(photoPath: String) {
        val currentUserId = _user.value?.id
        Log.d("updateUserPhotoPath", "UserId: $currentUserId")

        throw NotImplementedError("Not implemented")
    }

    //todo obsługa zadań powinna być osobnym view modelem
    private val _tasks = MutableLiveData<List<Task>>(emptyList())
    val tasks: LiveData<List<Task>> = _tasks

    fun addTask(task: Task) {
        _tasks.value = _tasks.value?.plus(task)
    }

    fun completeTask(taskId: Int) {
        _tasks.value = _tasks.value?.map { if (it.id == taskId) it.copy(status = "Completed") else it }
    }

    fun updateTask(task: Task) {
        _tasks.value = _tasks.value?.map { if (it.id == task.id) task else it }
    }

    fun deleteTask(taskId: Int) {
        Log.d("LoginViewModel", "Usunięcie zadania z ID: $taskId")
        _tasks.value = _tasks.value?.filter { it.id != taskId }
    }

    fun getTaskById(taskId: String?): Task? {
        return _tasks.value?.firstOrNull { it.id.toString() == taskId }
    }


    fun getTasksForDate(selectedDate: String): List<Task>? {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val selectedDateParsed = dateFormat.parse(selectedDate) ?: return emptyList()

        val taskDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())

        return tasks.value?.filter { task ->
            val taskStartDate = taskDateFormat.parse(task.startDate)
            val taskEndDate = taskDateFormat.parse(task.endDate)

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