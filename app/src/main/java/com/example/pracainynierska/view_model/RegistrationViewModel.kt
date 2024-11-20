package com.example.pracainynierska.view_model

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.pracainynierska.API.Exception.RequestValidationException
import com.example.pracainynierska.API.handler.registration.RegistrationHandler
import com.example.pracainynierska.API.handler.registration.RegistrationHandlerInterface
import com.example.pracainynierska.validator.PlayerDataValidator
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    private val registrationHandler : RegistrationHandlerInterface = RegistrationHandler()

    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var email by mutableStateOf("")

    val usernameProperty : String = "username"
    val emailProperty : String = "email"
    val passwordProperty : String = "password"
    val confirmPasswordProperty : String = "confirmPassword"

    private val validator = PlayerDataValidator()

    private val errorMessages = mutableMapOf(
        usernameProperty to mutableStateOf<String?>(null),
        emailProperty to mutableStateOf<String?>(null),
        passwordProperty to mutableStateOf<String?>(null),
        confirmPasswordProperty to mutableStateOf<String?>(null),
    )

    fun onUsernameChange(newUsername: String) = updateField(usernameProperty, newUsername, validator::validateUsername)
    fun onEmailChange(newEmail: String) = updateField(emailProperty, newEmail, validator::validateEmail)
    fun onPasswordChange(newPassword: String) = updateField(passwordProperty, newPassword, validator::validatePassword)
    fun onConfirmPasswordChange(confirmPassword: String) {
        this.confirmPassword = confirmPassword
        errorMessages[confirmPasswordProperty]?.value = validator.validatePasswordsMatch(password, confirmPassword)
    }

    private fun updateField(field: String, value: String, validationFn: (String) -> String?) {
        when (field) {
            usernameProperty -> username = value
            emailProperty -> email = value
            passwordProperty-> password = value
            confirmPasswordProperty -> confirmPassword = value
        }
        errorMessages[field]?.value = validationFn(value)
    }

    fun registerUser(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (fieldsAreEmpty()) {
            onError("All fields must be filled!")
            return
        }
        if (passwordsMismatch()) {
            onError("Passwords do not match!")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            onError("Invalid email format!")
            return
        }

        viewModelScope.launch {
            try {
                Log.d("RegistrationViewModel", "Registering user")
                registrationHandler.handle(username, email, password)
                onSuccess()
            } catch (e: RequestValidationException) {
                Log.d("RegistrationViewModel - Registration failed", e.getErrorDetailsMessage())
                onError("Registration failed. \n ${e.getErrorDetailsMessage()}")
            } catch (e: Exception) {
                Log.e("RegistrationViewModel - Registration failed", e.message.toString())
                onError("Registration failed")
            }
        }
    }

    private fun fieldsAreEmpty() = username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()
    private fun passwordsMismatch() = password != confirmPassword

    fun getUserNameErrorMessage() = errorMessages[usernameProperty]?.value
    fun getEmailErrorMessage() = errorMessages[emailProperty]?.value
    fun getPasswordErrorMessage() = errorMessages[passwordProperty]?.value
    fun getConfirmPasswordErrorMessage() = errorMessages[confirmPasswordProperty]?.value
}
