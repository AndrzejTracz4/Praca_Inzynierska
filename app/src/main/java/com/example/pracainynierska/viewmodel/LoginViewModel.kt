package com.example.pracainynierska.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pracainynierska.model.User
import com.example.pracainynierska.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var test by mutableStateOf("")
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

    var errorMessage by mutableStateOf<String?>(null)
        private set

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
        }else {
            null
        }
    }

    private fun validateEmail(email: String): String? {
        return if (email.isBlank()) "Email cannot be empty" else null
    }

    fun login(navController: NavController) {
        viewModelScope.launch {
            usernameErrorMessage = null
            passwordErrorMessage = null
            val user = userRepository.getUser(username, password)
            if (user != null) {
                loginSuccess = true
                navController.navigate("HomepageView")
            } else {
                usernameErrorMessage = "Invalid username or password"
                passwordErrorMessage = "Invalid username or password"
            }
        }
    }

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

        val user = User(username = username, email = email, password = password)
        viewModelScope.launch {
            try {
                userRepository.upsertUser(user)
                onSuccess()
            } catch (e: Exception) {
                onError("Registration failed: ${e.message}")
            }
        }
    }
}