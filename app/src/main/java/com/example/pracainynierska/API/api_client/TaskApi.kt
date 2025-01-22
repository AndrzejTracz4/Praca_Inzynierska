package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.context.PlayerContextInterface
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

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

        return request(Request
            .Builder()
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .url(buildPath(createPath))
            .post(body)
            .build()
        )
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