package com.example.pracainynierska.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.API.api_client.StatisticApi
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.statistic.StatisticManager

class AddStatisticViewModelFactory(
    private val playerContext: PlayerContextInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddStatisticViewModel::class.java)) {
            val api = StatisticApi(playerContext)
            val statisticManager = StatisticManager(api)
            return AddStatisticViewModel(playerContext, statisticManager) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}