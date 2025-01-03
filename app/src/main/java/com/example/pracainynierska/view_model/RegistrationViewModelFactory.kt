package com.example.pracainynierska.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.API.api_client.PlayerApi
import com.example.pracainynierska.API.handler.registration.RegistrationHandler
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.validator.PlayerDataValidator

class RegistrationViewModelFactory(
    private val playerContext: PlayerContextInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            val playerApi = PlayerApi(playerContext)
            val handler = RegistrationHandler(playerApi)
            val validator = PlayerDataValidator()

            return RegistrationViewModel(playerContext, handler, validator) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
