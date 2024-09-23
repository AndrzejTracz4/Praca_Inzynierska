package com.example.pracainynierska.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pracainynierska.model.User
import com.example.pracainynierska.repository.UserRepository
import kotlinx.coroutines.launch
import java.security.MessageDigest
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
    private val _loggedInUser = mutableStateOf<User?>(null)
    val loggedInUser: State<User?> get() = _loggedInUser

    // Ogólna zmienna przechowująca komunikaty o błędach
    var errorMessage by mutableStateOf<String?>(null)
        private set

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
    fun login(navController: NavController) {
        viewModelScope.launch {
            usernameErrorMessage = null
            passwordErrorMessage = null

            val hashedInputPassword = hashPassword(password)

            val user = userRepository.getUser(username, hashedInputPassword)

            if (user != null) {
                // Pobierz dane użytkownika
                fetchLoggedInUser(username)
                loginSuccess = true

                // Przekazanie nazwy użytkownika do HomepageView
                navController.navigate("HomepageView/${user.username}")
            } else {
                usernameErrorMessage = "Invalid username or password"
                passwordErrorMessage = "Invalid username or password"
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
        // Implementacja wysyłania emaila z nowym hasłem todo
    }

    //Pobiera dane zalogowanego użytkownika z bazy danych na podstawie podanego username,
    //viewModelScope to korutyna która działa asynchronicznie, czyli po prostu działa w tle
    private fun fetchLoggedInUser(username: String) {
        viewModelScope.launch {
            val user = userRepository.getUserByUsername(username)
            Log.d("LoginViewModel", "Fetched user: $user")
            _loggedInUser.value = user
            Log.d("LoginViewModel", "Logged in user set to: ${_loggedInUser.value}")
        }
    }
}