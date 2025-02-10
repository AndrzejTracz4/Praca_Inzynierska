package com.example.pracainynierska.manager.achievement

interface AchievementManagerInterface {
    suspend fun claim(id: Int): Boolean
}