package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.context.PlayerContextInterface
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class TaskApi(playerContext: PlayerContextInterface) : ApiDetails(playerContext) {
    private val createPath : String = "api/tasks"

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

        val taskRequest = Request
            .Builder()
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .url(buildPath(createPath))
            .post(body)
            .build()

        try {
            val response = apiClient.newCall(taskRequest).execute()
            if (!response.isSuccessful) {
                throw IOException("Unexpected code: ${response.code}")
            }
        } catch (e: IOException) {
            Log.e("Task API", "Error during request", e)
            throw e
        }

        // TODO: WAITING FOR SERVER TO ADD THIS CODE
//        return suspendCoroutine { continuation ->
//            request(taskRequest, Task.serializer()) { result ->
//                result.onSuccess { task ->
//                    Log.d("Task API", "Received task: $task")
//                    continuation.resume(task)
//                }.onFailure { error ->
//                    Log.e("Task API", "Error: ${error.message}")
//                    continuation.resumeWithException(error)
//                }
//            }
//        }
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