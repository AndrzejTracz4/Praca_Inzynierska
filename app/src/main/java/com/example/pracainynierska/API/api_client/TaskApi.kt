package com.example.pracainynierska.API.api_client

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.context.PlayerContextInterface
import kotlinx.serialization.builtins.ListSerializer
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TaskApi(playerContext: PlayerContextInterface) : ApiDetails(playerContext) {
    private val taskPath: String = "api/tasks"

    private val completeTaskPath: String = "api/task"

    suspend fun addTask(
        type: String,
        name: String,
        description: String,
        category: Int,
        difficulty: String,
        startsAt: String,
        endsAt: String,
        measureUnit: String,
        interval: Int
    ): Task? {
        val body =
            getCreateRequestBody(
                type, name, description,
                category, difficulty, startsAt, endsAt, measureUnit, interval
            )
        Log.d("Task API", "Created body")

        val taskRequest = Request
            .Builder()
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .url(buildPath(taskPath))
            .post(body)
            .build()

        return suspendCoroutine { continuation ->
            request(taskRequest, Task.serializer()) { result ->
                result.onSuccess { task ->
                    Log.d("Task API", "Received task: $task")
                    continuation.resume(task)
                }.onFailure { error ->
                    Log.e("Task API", "Error: ${error.message}")
                    continuation.resumeWithException(error)
                }
            }
        }
    }

    suspend fun getTasks(): List<Task> {
        val tasksRequest = Request
            .Builder()
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .url(buildPath(taskPath))
            .get()
            .build()

        Log.d("Task API", "Getting tasks")

        return suspendCoroutine { continuation ->
            request(tasksRequest, ListSerializer(Task.serializer())) { result ->
                result.onSuccess { task ->
                    Log.d("Task API", "Received tasks: $task")
                    continuation.resume(task)
                }.onFailure { error ->
                    Log.e("Task API", "Error: ${error.message}")
                    continuation.resumeWithException(error)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getTasksByDate(date: String): List<Task> {
        val parsedDate = LocalDate.parse(date)

        val startOfDay = parsedDate.atStartOfDay().format(DateTimeFormatter.ISO_DATE_TIME)
        val endOfDay = parsedDate.atTime(LocalTime.MAX).format(DateTimeFormatter.ISO_DATE_TIME)

        val url = buildPath("$taskPath?startsAt[after]=$startOfDay&startsAt[before]=$endOfDay")

        val tasksRequest = Request
            .Builder()
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .url(url)
            .get()
            .build()

        return suspendCoroutine { continuation ->
            request(tasksRequest, ListSerializer(Task.serializer())) { result ->
                result.onSuccess { task ->
                    Log.d("Task API", "Received tasks: $task")
                    continuation.resume(task)
                }.onFailure { error ->
                    Log.e("Task API", "Error: ${error.message}")
                    continuation.resumeWithException(error)
                }
            }
        }
    }

    suspend fun completeTask(id: Int): Boolean {
        val taskRequest = Request
            .Builder()
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .url(buildPath("$completeTaskPath/$id/complete"))
            .patch("".toRequestBody("application/merge-patch+json".toMediaTypeOrNull()))
            .build()

        return suspendCoroutine { continuation ->
            request(taskRequest, Task.serializer()) { result ->
                result.onSuccess {
                    Log.d("Task API", "Completed task")
                    continuation.resume(true)
                }.onFailure { error ->
                    Log.e("Task API", "Error: ${error.message}")
                    continuation.resume(false)
                }
            }
        }
    }

    private fun getCreateRequestBody(
        type: String,
        name: String,
        description: String,
        category: Int,
        difficulty: String,
        startsAt: String,
        endsAt: String,
        measureUnit: String,
        interval: Int
    ): RequestBody {
        val json = """
                {
                    "type": "$type",
                    "name": "$name",
                    "description": "$description",
                    "category": "/api/categories/$category",
                    "difficulty": "$difficulty",
                    "startsAt": "$startsAt",
                    "endsAt": "$endsAt",
                    "measureUnit": "$measureUnit",
                    "interval": $interval
                }
            """.trimIndent()
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())
        return body
    }
}