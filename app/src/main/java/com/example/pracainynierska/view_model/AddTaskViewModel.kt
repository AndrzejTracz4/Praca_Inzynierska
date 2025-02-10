package com.example.pracainynierska.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.Exception.RequestValidationException
import com.example.pracainynierska.API.model.Category
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.dictionary.TaskDifficulty
import com.example.pracainynierska.dictionary.types.TaskType
import com.example.pracainynierska.manager.task.TaskManagerInterface
import kotlinx.coroutines.launch

class AddTaskViewModel(
    pc: PlayerContextInterface,
    private val taskManager: TaskManagerInterface
) : AbstractViewModel(pc) {

    val tasks = taskManager.get()

    fun add(
        name: String,
        difficulty: TaskDifficulty,
        category: Category,
        startsAt: String,
        endsAt: String,
        interval: Int,
        measureUnit: String,
        type: TaskType,
        description: String
    ) {
        viewModelScope.launch {
            try {
                taskManager.add(name, difficulty, category, startsAt, endsAt, interval, measureUnit, type, description)
            } catch (e: RequestValidationException) {
                Log.e("AddCategoryViewModel", "CreationError - validation exception")
            } catch (e: Exception) {
                Log.e("AddCategoryViewModel - Creation failed", e.message.toString())
            }
        }
        Log.d("AddTaskViewModel", "tasks: $tasks")
    }

}