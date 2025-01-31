package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.context.PlayerContextInterface
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.serialization.json.Json


class TaskApi(playerContext: PlayerContextInterface) : ApiDetails(playerContext) {
    private val taskPath : String = "api/tasks"

    fun addTask(
        type: String,
        name: String,
        description: String,
        category: String,
        difficulty: String,
        startsAt: String,
        endsAt: String
    ) {
        val body = getCreateRequestBody(type, name, description, category, difficulty, startsAt, endsAt)
        Log.d("Task API", "Created body")

        return request(Request
            .Builder()
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .url(buildPath(taskPath))
            .post(body)
            .build()
        )
    }

    fun getTasks(): List<Task> {
        val tasksRequest = Request
            .Builder()
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .url(buildPath(taskPath))
            .get()
            .build()

        val tasksResponse = apiClient.newCall(tasksRequest).execute()
        val tasksResponseBody = tasksResponse.body?.string()
        Log.d("Task API", tasksResponseBody.toString())

        return if (tasksResponse.isSuccessful && tasksResponseBody != null) {
            Json.decodeFromString<List<Task>>(tasksResponseBody)
        } else {
            emptyList()
        }
    }

    fun completeTask(id: Int) {
        val taskRequest = Request
            .Builder()
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .url(buildPath("$taskPath/$id/complete"))
            .patch(RequestBody.create(null, ByteArray(0)))
            .build()

        val response = apiClient.newCall(taskRequest).execute()

        if (response.isSuccessful) {
            Log.d("Task API", "Task $id marked as complete")
        } else {
            Log.e("Task API", "Failed to complete task $id: ${response.code}")
        }
    }



    private fun getCreateRequestBody(
        type: String,
        name: String,
        description: String,
        category: String,
        difficulty: String,
        startsAt: String,
        endsAt: String
    ): RequestBody {

        val json = """
                {
                    "type": "$type",
                    "name": "$name",
                    "description": "$description",
                    "category": "$category",
                    "difficulty": "$difficulty",
                    "startsAt": "$startsAt",
                    "endsAt": "$endsAt"
                }
            """.trimIndent()
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())
        return body
    }
}