package com.example.pracainynierska.manager.achievement

import com.example.pracainynierska.API.api_client.AchievementApi
import com.example.pracainynierska.API.model.Achievement
import com.example.pracainynierska.dictionary.AchievementStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AchievementManager(
    private val apiClient: AchievementApi
) : AchievementManagerInterface {

    override suspend fun claim(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            apiClient.claimAchievement(id)
        }
    }
}