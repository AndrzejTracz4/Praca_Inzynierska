package com.example.pracainynierska.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.Exception.RequestValidationException
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.statistic.StatisticManagerInterface
import kotlinx.coroutines.launch

class EditStatisticViewModel(
    pc: PlayerContextInterface,
    private val statisticManager: StatisticManagerInterface
) : AbstractViewModel(pc) {
    fun editStatistic(id: Int, name: String, iconPath: String, onSuccess: () -> Unit, onError: () -> Unit){
        viewModelScope.launch {
            try {
                Log.d("EditStatisticViewModel", "Editing statistic")

                val updatedStatistic = statisticManager.edit(id, name, iconPath)

                Log.d("EditStatisticViewModel", "Statistic updated: $updatedStatistic")

                if (updatedStatistic != null) {
                    playerContext.editPlayerStatistic(updatedStatistic)
                    onSuccess()
                }
            } catch (e: RequestValidationException) {
                Log.e("EditStatisticViewModel", "EditingError - validation exception")
                onError()
            } catch (e: Exception) {
                Log.e("EditStatisticViewModel - Editing failed", e.message.toString())
                onError()
            }
        }
    }

    fun deleteStatistic(id: Int, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("EditStatisticViewModel", "Deleting statistic")

                val deletedStatistic = statisticManager.delete(id)

                Log.d("EditStatisticViewModel", "Statistic deteled: $deletedStatistic")

                if (deletedStatistic) {
                    playerContext.removePlayerStatistic(id)
                    onSuccess()
                } else {
                    onError()
                }
            } catch (e: RequestValidationException) {
                Log.e("EditStatisticViewModel", "EditingError - validation exception")
                onError()
            } catch (e: Exception) {
                Log.e("EditStatisticViewModel - Editing failed", e.message.toString())
                onError()
            }
        }
    }
}