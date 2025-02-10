package com.example.pracainynierska.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.Exception.RequestValidationException
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.task.TaskManagerInterface
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class CalendarsViewModel(
    pc: PlayerContextInterface,
    private val taskManager: TaskManagerInterface
) : AbstractViewModel(pc) {

    private var isFetching = false

    suspend fun getTasksByDate(date: String): List<Task> {
        return if (isFetching) {
            emptyList()
        } else {
            isFetching = true
            return runCatching {
                taskManager.getByDate(date)
            }.onFailure { e ->
                Log.e("CalendarsViewModel", "Getting failed: ${e.message}")
            }.getOrDefault(emptyList()).also {
                isFetching = false
            }
        }
    }

    fun completeTask(id: Int) {
        viewModelScope.launch {
            try {
                taskManager.complete(id)
            } catch (e: RequestValidationException) {
                Log.e("CalendarsViewModel", "CompletingError - validation exception")
            } catch (e: Exception) {
                Log.e("CalendarsViewModel - Completing failed", e.message.toString())
            }
        }
    }

}