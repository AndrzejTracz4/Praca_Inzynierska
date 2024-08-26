package com.example.pracainynierska.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pracainynierska.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var loginSuccess by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onUsernameChange(newUsername: String) {
        username = newUsername
        errorMessage = validateUsername(newUsername)
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        errorMessage = validatePassword(newPassword)
    }

    private fun validateUsername(username: String): String? {
        return if (username.isBlank()) "Username cannot be empty!" else null
    }

    private fun validatePassword(password: String): String? {
        return if (password.isBlank()) "Password cannot be empty" else null
    }

    fun login(navController: NavController) {
        viewModelScope.launch {
            errorMessage = null
            val user = userRepository.getUser(username, password)
            if (user != null) {
                loginSuccess = true
                navController.navigate("HomepageView")
            } else {
                errorMessage = "Invalid username or password"
            }
        }
    }

    fun register(navController: NavController) {
        viewModelScope.launch {
            //todo
        }
    }
}