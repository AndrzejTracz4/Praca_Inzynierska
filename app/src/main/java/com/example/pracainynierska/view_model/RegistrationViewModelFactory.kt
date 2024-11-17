package com.example.pracainynierska.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RegistrationViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
