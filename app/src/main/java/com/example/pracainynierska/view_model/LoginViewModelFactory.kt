package com.example.pracainynierska.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.API.api_client.PlayerApi
import com.example.pracainynierska.API.handler.authorization.PlayerAuthorizationHandler
import com.example.pracainynierska.context.PlayerContextInterface

class LoginViewModelFactory(
    private val playerContext: PlayerContextInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val playerApi = PlayerApi(playerContext)
            val playerAuthorizationHandler = PlayerAuthorizationHandler(playerApi)

            return LoginViewModel(playerContext, playerAuthorizationHandler) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
