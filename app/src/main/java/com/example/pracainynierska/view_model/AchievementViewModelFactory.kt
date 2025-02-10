package com.example.pracainynierska.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.API.api_client.PlayerApi
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.achievement.AchievementManagerInterface

class AchievementViewModelFactory(
    private val playerContext: PlayerContextInterface,
    private val achievementManager: AchievementManagerInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AchievementViewModel::class.java)) {
            return AchievementViewModel(playerContext, achievementManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}