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
import com.example.pracainynierska.manager.reset_code.ResetCodeManagerInterface
import kotlinx.coroutines.launch

class ForgotPasswordViewModel (
    private val resetCodeManager: ResetCodeManagerInterface
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var emailErrorMessageId by mutableIntStateOf(0)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
        emailErrorMessageId = validateEmail(newEmail)
    }

    private fun validateEmail(email: String): Int {
        return when {
            email.isBlank() -> R.string.validation_email_cannot_be_empty
            !email.contains("@") || !email.contains(".") -> R.string.validation_invalid_email_format
            else -> 0
        }
    }

    fun getResetCode(onSuccess: () -> Unit, onError: () -> Unit) {
        if (validateEmail(email) != 0) {
            return
        }

        viewModelScope.launch {
            try {
                Log.d("ForgotPasswordViewModel", "Sending reset code")

                resetCodeManager.get(email)

                Log.d("ForgotPasswordViewModel", "Reset code sent successfully")

                onSuccess()

            } catch (e: RequestValidationException) {
                Log.e("ForgotPasswordViewModel", "Validation error: ${e.message}")
                onError()
            } catch (e: Exception) {
                Log.e("ForgotPasswordViewModel", "Sending reset code failed: ${e.message}")
                onError()
            }
        }
    }
}