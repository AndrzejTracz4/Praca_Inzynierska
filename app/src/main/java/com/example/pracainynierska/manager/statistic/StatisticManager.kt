package com.example.pracainynierska.manager.statistic

import com.example.pracainynierska.API.api_client.StatisticApi
import com.example.pracainynierska.API.model.Statistic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StatisticManager(private val apiClient: StatisticApi) : StatisticManagerInterface {
    override suspend fun add(name: String, iconPath: String): Statistic? {
        return withContext(Dispatchers.IO){
            apiClient.addStatistic(name, iconPath)
        }
    }

    override suspend fun edit(id: Int, name: String, iconPath: String): Statistic? {
        return withContext(Dispatchers.IO){
            apiClient.editStatistic(id, name, iconPath)
        }
    }

    override suspend fun delete(id: Int): Boolean {
        return withContext(Dispatchers.IO){
            apiClient.deleteStatistic(id)
        }
    }
}