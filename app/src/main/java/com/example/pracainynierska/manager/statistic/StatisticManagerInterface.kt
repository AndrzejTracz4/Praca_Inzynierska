package com.example.pracainynierska.manager.statistic

import com.example.pracainynierska.API.model.Statistic

interface StatisticManagerInterface {
    suspend fun add(name: String, iconPath: String): Statistic?
    suspend fun edit(id: Int, name: String, iconPath: String): Statistic?
    suspend fun delete(id: Int): Boolean
}
