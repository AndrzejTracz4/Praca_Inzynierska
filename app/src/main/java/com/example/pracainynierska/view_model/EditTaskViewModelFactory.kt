package com.example.pracainynierska.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.API.api_client.PlayerApi
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.task.TaskManagerInterface

class EditTaskViewModelFactory(
    private val playerContext: PlayerContextInterface,
    private val taskManager: TaskManagerInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditTaskViewModel::class.java)) {
            return EditTaskViewModel(playerContext, taskManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}