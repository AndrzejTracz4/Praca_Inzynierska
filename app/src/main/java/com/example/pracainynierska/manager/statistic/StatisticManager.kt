package com.example.pracainynierska.manager.statistic

import android.util.Log
import com.example.pracainynierska.API.api_client.StatisticApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StatisticManager(private val apiClient: StatisticApi) : StatisticManagerInterface {
    override suspend fun add(name: String, iconPath: String) {
        return withContext(Dispatchers.IO) {
            apiClient.addStatistic(name, iconPath)
        }
    }
}