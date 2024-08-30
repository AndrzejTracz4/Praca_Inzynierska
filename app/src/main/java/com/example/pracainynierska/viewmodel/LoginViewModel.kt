package com.example.pracainynierska.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pracainynierska.model.User
import com.example.pracainynierska.repository.UserRepository
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

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

                if (existingUserByUsername != null) {
                    onError("Username is already taken")
                    return@launch
                }

                if (existingUserByEmail != null) {
                    onError("Email is already registered")
                    return@launch
                }

                // Hashowanie hasła przed zapisaniem do bazy
                val hashedPassword = hashPassword(password)

                // Tworzenie nowego użytkownika i zapisanie go do bazy
                val user = User(username = username, email = email, password = hashedPassword)
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

                val newPassword = generateRandomPassword()

                val hashedForgotPassword = hashPassword(newPassword)

                existingUser.password = hashedForgotPassword
                userRepository.upsertUser(existingUser)

                sendEmailWithNewPassword(email, newPassword)

                onSuccess()
            } catch (e: Exception) {
                onError("Wystąpił błąd: ${e.message}")
            }
        }
    }

    // Funkcja generująca nowe hasło
    private fun generateRandomPassword(): String {
        return UUID.randomUUID().toString().take(12) // Generuje losowe hasło o długości 12 znaków
    }

    // Funkcja wysyłająca email z nowym hasłem
    private fun sendEmailWithNewPassword(email: String, newPassword: String) {
        // Implementacja wysyłania emaila z nowym hasłem
    }
}