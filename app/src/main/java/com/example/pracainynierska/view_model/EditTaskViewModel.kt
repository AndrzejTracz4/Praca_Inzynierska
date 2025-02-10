package com.example.pracainynierska.view_model

import android.util.Log
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.task.TaskManagerInterface

class EditTaskViewModel(
    pc: PlayerContextInterface,
    private val taskManager: TaskManagerInterface
) : AbstractViewModel(pc) {

    val tasks = taskManager.get()

    fun getTaskById(taskId: String?): Task? {
        return tasks.value?.firstOrNull { it.id.toString() == taskId }
    }

}