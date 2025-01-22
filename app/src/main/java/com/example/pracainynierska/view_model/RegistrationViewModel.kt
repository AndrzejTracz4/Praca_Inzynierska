package com.example.pracainynierska.view_model

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.pracainynierska.API.Exception.RequestValidationException
import com.example.pracainynierska.API.handler.registration.RegistrationHandlerInterface
import com.example.pracainynierska.API.model.error_response.ValidationErrorResponse
import com.example.pracainynierska.R
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.validator.PlayerDataValidator
import kotlinx.coroutines.launch

class RegistrationViewModel(
    playerContext: PlayerContextInterface,
    private val registrationHandler: RegistrationHandlerInterface,
    private val validator: PlayerDataValidator
) : AbstractViewModel(playerContext) {

    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var email by mutableStateOf("")

    val usernameProperty : String = "name"
    val emailProperty : String = "email"
    val passwordProperty : String = "password"
    val confirmPasswordProperty : String = "confirmPassword"

    private val errorMessages = mutableMapOf(
        usernameProperty to mutableIntStateOf(0),
        emailProperty to mutableIntStateOf(0),
        passwordProperty to mutableIntStateOf(0),
        confirmPasswordProperty to mutableIntStateOf(0),
    )

    fun onUsernameChange(newUsername: String) = updateField(usernameProperty, newUsername, validator::validateUsername)
    fun onEmailChange(newEmail: String) = updateField(emailProperty, newEmail, validator::validateEmail)
    fun onPasswordChange(newPassword: String) = updateField(passwordProperty, newPassword, validator::validatePassword)
    fun onConfirmPasswordChange(confirmPassword: String) {
        this.confirmPassword = confirmPassword
        errorMessages[confirmPasswordProperty]?.intValue = validator.validatePasswordsMatch(password, confirmPassword)
    }

    private fun updateField(field: String, value: String, validationFn: (String) -> Int) {
        when (field) {
            usernameProperty -> username = value
            emailProperty -> email = value
            passwordProperty-> password = value
            confirmPasswordProperty -> confirmPassword = value
        }
        errorMessages[field]?.intValue = validationFn(value)
    }

    fun registerUser(onSuccess: () -> Unit, onError: (Int) -> Unit) {
        if (fieldsAreEmpty()) {
            onError(R.string.validation_all_fields_must_be_filled)
            return
        }
        if (passwordsMismatch()) {
            onError(R.string.validation_passwords_does_not_match)
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            onError(R.string.validation_invalid_email_format)
            return
        }

        viewModelScope.launch {
            try {
                Log.d("RegistrationViewModel", "Registering user")
                registrationHandler.handle(username, email, password)
                onSuccess()
            } catch (e: RequestValidationException) {
                Log.e("RegistrationViewModel", "Registration failed - validation exception")
                onError(R.string.registration_failed)
            } catch (e: Exception) {
                Log.e("RegistrationViewModel - Registration failed", e.message.toString())
                onError(R.string.registration_failed)
            }
        }
    }

    private fun fieldsAreEmpty() = username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()
    private fun passwordsMismatch() = password != confirmPassword

    fun getUserNameErrorMessage() = errorMessages[usernameProperty]!!.intValue
    fun getEmailErrorMessage() = errorMessages[emailProperty]!!.intValue
    fun getPasswordErrorMessage() = errorMessages[passwordProperty]!!.intValue
    fun getConfirmPasswordErrorMessage() = errorMessages[confirmPasswordProperty]!!.intValue
}
