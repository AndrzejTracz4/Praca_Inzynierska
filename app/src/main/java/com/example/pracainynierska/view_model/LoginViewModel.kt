package com.example.pracainynierska.view_model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.handler.authorization.AuthorizationHandlerInterface
import com.example.pracainynierska.R
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.achievement.AchievementManager
import com.example.pracainynierska.manager.achievement.AchievementManagerInterface
import com.example.pracainynierska.manager.task.TaskManagerInterface
import com.example.pracainynierska.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LoginViewModel(
    pc: PlayerContextInterface,
    private val playerAuthorizationHandler: AuthorizationHandlerInterface,
    private val taskManager: TaskManagerInterface,
    private val achievementManager: AchievementManagerInterface
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

    var usernameErrorMessageId by mutableIntStateOf(0)
        private set

    var passwordErrorMessageId by mutableIntStateOf(0)
        private set

    var confirmPasswordErrorMessageId by mutableIntStateOf(0)
        private set

    var emailErrorMessageId by mutableIntStateOf(0)
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

    var newPasswordErrorMessageId by mutableIntStateOf(0)
        private set

    var confirmNewPasswordErrorMessageId by mutableIntStateOf(0)
        private set

    fun onNewPasswordChange(newPassword: String) {
        this.newPassword = newPassword
        newPasswordErrorMessageId = validatePassword(newPassword)
    }

    fun onConfirmNewPasswordChange(newConfirmPassword: String) {
        confirmNewPassword = newConfirmPassword
        validateNewPasswords()
    }

    private fun validateNewPasswords() {
        confirmNewPasswordErrorMessageId = if (newPassword != confirmNewPassword) {
            R.string.validation_passwords_does_not_match
        } else {
            0
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
        usernameErrorMessageId = validateUsername(newUsername)
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        passwordErrorMessageId = validatePassword(newPassword)
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
        emailErrorMessageId = validateEmail(newEmail)
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
        validatePasswords()
    }

    private fun validateUsername(username: String): Int {
        return if (username.isBlank()) R.string.validation_username_cannot_be_empty else 0
    }

    private fun validatePassword(password: String): Int {
        return if (password.isBlank()) R.string.validation_password_cannot_be_empty else 0
    }

    private fun validatePasswords() {
        confirmPasswordErrorMessageId = if (password != confirmPassword) {
            R.string.validation_passwords_does_not_match
        } else {
            0
        }
    }

    private fun validateEmail(email: String): Int {
        return if (email.isBlank()) R.string.validation_email_cannot_be_empty else 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun login(onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val player = playerAuthorizationHandler.authorize(email, password)
            if (player != null) {
                initializeUserData()
                Log.d("LoginViewModel", "Player: $player")
                loginSuccess = true
                onLoginResult(true)
            } else {
                Log.d("LoginViewModel", "Player is null")
                emailErrorMessageId = R.string.invalid_username_or_password
                passwordErrorMessageId = R.string.invalid_username_or_password
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

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initializeUserData(){
        withContext(Dispatchers.IO) {
            val today = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formattedDate = today.format(formatter)
            Log.d("initializeUserData", "Formatted date: $formattedDate")
            taskManager.getByDate(formattedDate)
        }
    }
}