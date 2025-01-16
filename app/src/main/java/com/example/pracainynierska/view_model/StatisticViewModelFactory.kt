package com.example.pracainynierska.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.context.PlayerContextInterface

class StatisticViewModelFactory(
    private val playerContext: PlayerContextInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticViewModel::class.java)) {
            return StatisticViewModel(playerContext) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
