package com.example.pracainynierska.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.Exception.RequestValidationException
import com.example.pracainynierska.R
import com.example.pracainynierska.manager.password_reset.PasswordResetManagerInterface
import kotlinx.coroutines.launch

class ChangePasswordViewModel(
    private val resetCodeManager: PasswordResetManagerInterface
) : ViewModel() {

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var passwordErrorMessageId by mutableIntStateOf(0)
        private set

    var confirmPasswordErrorMessageId by mutableIntStateOf(0)
        private set

    fun onPasswordChange(newPassword: String) {
        this.password = newPassword
        passwordErrorMessageId = validatePassword(newPassword)
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
        validatePasswords()
    }

    private fun validatePasswords(): Boolean {
        return if (password != confirmPassword) {
            confirmPasswordErrorMessageId = R.string.validation_passwords_does_not_match
            false
        } else {
            confirmPasswordErrorMessageId = 0
            true
        }
    }

    private fun validatePassword(password: String): Int {
        return when {
            password.isBlank() -> R.string.validation_password_cannot_be_empty
            password.length < 8 -> R.string.validation_password_must_be_at_least_8_characters_long
            else -> 0
        }
    }


    fun changePassword(onSuccess: () -> Unit, onError: () -> Unit) {
        if (!validatePasswords()) {
            return
        }

        viewModelScope.launch {
            try {
                Log.d("ChangePasswordViewModel", "Changing password")

                resetCodeManager.changePassword(password)

                Log.d("ChangePasswordViewModel", "Successfully changed password")

                onSuccess()

                password = ""
                confirmPassword = ""

            } catch (e: RequestValidationException) {
                Log.e("ChangePasswordViewModel", "Validation error: ${e.message}")
                onError()
            } catch (e: Exception) {
                Log.e("ChangePasswordViewModel", "Changing password error: ${e.message}")
                onError()
            }
        }
    }

}