package com.example.pracainynierska.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.manager.password_reset.PasswordResetManagerInterface

class ResetCodeViewModelFactory(
    private val resetCodeManager: PasswordResetManagerInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResetCodeViewModel::class.java)) {
            return ResetCodeViewModel(resetCodeManager) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}