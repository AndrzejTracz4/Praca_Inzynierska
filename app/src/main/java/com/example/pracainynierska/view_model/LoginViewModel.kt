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
import com.example.pracainynierska.model.User
import kotlinx.coroutines.launch
import java.security.MessageDigest

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
}