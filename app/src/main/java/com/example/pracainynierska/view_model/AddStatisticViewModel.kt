package com.example.pracainynierska.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.Exception.RequestValidationException
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.statistic.StatisticManagerInterface
import kotlinx.coroutines.launch

class AddStatisticViewModel(
    pc: PlayerContextInterface,
    private val statisticManager: StatisticManagerInterface
) : AbstractViewModel(pc) {
    fun addStatistic(name: String, iconPath: String) {
        viewModelScope.launch {
            try {
                Log.d("AddStatisticViewModel", "Adding statistic")
                statisticManager.add(name, iconPath)
            } catch (e: RequestValidationException) {
                Log.e("AddStatisticViewModel", "CreationError - validation exception")
                throw e
            } catch (e: Exception) {
                Log.e("AddStatisticViewModel - Creation failed", e.message.toString())
                throw e
            }
        }
    }
}