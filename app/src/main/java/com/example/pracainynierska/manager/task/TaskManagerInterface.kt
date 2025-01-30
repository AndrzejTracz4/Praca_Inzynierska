package com.example.pracainynierska.manager.task

import androidx.lifecycle.LiveData
import com.example.pracainynierska.API.model.Task

interface TaskManagerInterface {
    suspend fun addTask(
        type: String,
        name: String,
        description: String,
        category: String,
        difficulty: String,
        startsAt: String,
        endsAt: String
    )

    fun getTasksList(): LiveData<List<Task>>
    fun getTasks(): List<Task>

    suspend fun getTasksViaApi()
}