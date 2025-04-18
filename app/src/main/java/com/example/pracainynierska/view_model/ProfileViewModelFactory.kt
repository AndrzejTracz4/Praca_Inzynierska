package com.example.pracainynierska.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.API.api_client.PlayerApi
import com.example.pracainynierska.context.PlayerContextInterface

class ProfileViewModelFactory (
    private val playerContext: PlayerContextInterface,
    private val appContext: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val playerApi = PlayerApi(playerContext)
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(playerContext, playerApi, appContext) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}