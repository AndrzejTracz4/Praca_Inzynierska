package com.example.pracainynierska.view_model

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.API.api_client.PlayerApi
import com.example.pracainynierska.API.handler.authorization.PlayerAuthorizationHandler
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.daily_challenge.DailyChallengeManagerInterface
import com.example.pracainynierska.manager.task.TaskManagerInterface

class LoginViewModelFactory(
    private val playerContext: PlayerContextInterface,
    private val taskManager: TaskManagerInterface,
    private val dailyChallengeManager: DailyChallengeManagerInterface,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val playerApi = PlayerApi(playerContext)
            val playerAuthorizationHandler = PlayerAuthorizationHandler(playerApi)

            return LoginViewModel(
                playerContext,
                playerAuthorizationHandler,
                taskManager,
                dailyChallengeManager,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
