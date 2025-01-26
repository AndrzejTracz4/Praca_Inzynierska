package com.example.pracainynierska.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.manager.reset_code.ResetCodeManagerInterface

class ForgotPasswordViewModelFactory (
    private val resetCodeManager: ResetCodeManagerInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java)) {
            return ForgotPasswordViewModel(resetCodeManager) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
    }