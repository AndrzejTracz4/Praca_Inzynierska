package com.example.pracainynierska.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.Exception.RequestValidationException
import com.example.pracainynierska.manager.reset_code.ResetCodeManagerInterface
import kotlinx.coroutines.launch

class ResetCodeViewModel(
    private val resetCodeManager: ResetCodeManagerInterface
) : ViewModel() {

    fun verifyResetCode(code: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("ResetCodeViewModel", "Verifying reset code")

                resetCodeManager.verify(code)

                Log.d("ResetCodeViewModel", "Reset code verified successfully")

                onSuccess()

            } catch (e: RequestValidationException) {
                Log.e("ResetCodeViewModel", "Validation error: ${e.message}")
                onError()
            } catch (e: Exception) {
                Log.e("ResetCodeViewModel", "Reset code verification failed: ${e.message}")
                onError()
            }
        }
    }
}