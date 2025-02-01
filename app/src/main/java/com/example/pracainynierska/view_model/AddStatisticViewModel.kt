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
    fun addStatistic(name: String, iconPath: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("AddStatisticViewModel", "Adding statistic")

                val newStatistic = statisticManager.add(name, iconPath)

                Log.d("AddStatisticViewModel", "Statistic added: $newStatistic")

                if (newStatistic != null) {
                    playerContext.addPlayerStatistic(newStatistic)
                    onSuccess()
                }
            } catch (e: RequestValidationException) {
                Log.e("AddStatisticViewModel", "CreationError - validation exception")
                onError()
            } catch (e: Exception) {
                Log.e("AddStatisticViewModel - Creation failed", e.message.toString())
                onError()
            }
        }
    }
}