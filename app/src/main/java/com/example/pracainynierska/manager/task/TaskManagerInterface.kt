package com.example.pracainynierska.manager.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pracainynierska.API.model.Category
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.dictionary.TaskDifficulty
import com.example.pracainynierska.dictionary.types.TaskType

interface TaskManagerInterface {
    suspend fun add(
        name: String,
        difficulty: TaskDifficulty,
        category: Category,
        startsAt: String,
        endsAt: String,
        interval: Int,
        measureUnit: String,
        type: TaskType,
        description: String
    )

    fun get(): LiveData<List<Task>>

    suspend fun getByDate(date: String): List<Task>

    suspend fun complete(id: Int)
}