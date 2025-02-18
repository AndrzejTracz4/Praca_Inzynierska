package com.example.pracainynierska.manager.task

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pracainynierska.API.api_client.TaskApi
import com.example.pracainynierska.API.model.Category
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.dictionary.TaskDifficulty
import com.example.pracainynierska.dictionary.TaskStatus
import com.example.pracainynierska.dictionary.types.TaskType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TaskManager(
    private val apiClient: TaskApi
) : TaskManagerInterface {

    private val _tasks = MutableLiveData<List<Task>>()
    private val tasks: LiveData<List<Task>> = _tasks

    private val checkedDates = mutableSetOf<String>()

    override fun get(): LiveData<List<Task>> {
        return tasks
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getByDate(date: String): List<Task> {
        return withContext(Dispatchers.IO) {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val cachedTasks = _tasks.value ?: emptyList()

            val filteredTasks = cachedTasks.filter {
                val taskDate = LocalDate.parse(it.startsAt.substring(0, 10), dateFormatter)
                taskDate == LocalDate.parse(date, dateFormatter)
            }

            if (filteredTasks.isNotEmpty()) {
                return@withContext filteredTasks
            }

            if (checkedDates.contains(date)) {
                return@withContext emptyList()
            }

            val newTasks = apiClient.getTasksByDate(date)

            Log.d("TaskManager", "New tasks: $newTasks")

            if (newTasks.isEmpty()) {
                checkedDates.add(date)
                return@withContext emptyList()
            }

            val updatedTasks = (cachedTasks + newTasks).distinctBy { it.id }
            _tasks.postValue(updatedTasks)

            return@withContext newTasks
        }
    }


    override suspend fun add(
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
        return withContext(Dispatchers.IO) {
            try {
                val newTask: Task? = apiClient.addTask(
                    type.key,
                    name,
                    description,
                    category.id,
                    difficulty.key,
                    startsAt,
                    endsAt,
                    measureUnit,
                    interval
                )

                if (newTask == null) {
                    return@withContext
                }

                val currentTasks = _tasks.value ?: emptyList()
                val updatedTasks = (currentTasks + newTask).distinctBy { it.id }

                _tasks.postValue(updatedTasks)

            } catch (e: Exception) {
                Log.e("TaskManager", "Error: ${e.message}")
            }
        }
    }

    override suspend fun complete(id: Int) {
        return withContext(Dispatchers.IO) {
            val response = apiClient.completeTask(id)
            if (response) {
                val updatedTasks = _tasks.value?.map { task ->
                    if (task.id == id) {
                        task.copy(status = TaskStatus.COMPLETED.key)
                    } else {
                        task
                    }
                }

                updatedTasks?.let {
                    _tasks.postValue(it)
                }
            }
        }
    }

    override suspend fun getDailyChallenge(): Task? {
        return withContext(Dispatchers.IO) {
            apiClient.getDailyChallengeTask()
        }
    }

}
