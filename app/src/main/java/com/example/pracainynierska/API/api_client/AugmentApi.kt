package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.API.model.Augment
import com.example.pracainynierska.context.PlayerContextInterface
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AugmentApi(playerContext: PlayerContextInterface) : ApiDetails(playerContext) {
    private val createPath : String = "api/augments"

    suspend fun addAugment(type: String, validForDays: Int, multiplier: Int, category: String): Augment? {
        val body = getCreateRequestBody(type, validForDays, multiplier, category)
        Log.d("Augment API", "Created body")

        val augmentRequest = Request
            .Builder()
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .url(buildPath(createPath))
            .post(body)
            .build()

        return suspendCoroutine { continuation ->
            request(augmentRequest, Augment.serializer()) { result ->
                result.onSuccess { augment ->
                    Log.d("Augment API", "Received augment: $augment")
                    continuation.resume(augment)
                }.onFailure { error ->
                    Log.e("Augment API", "Error: ${error.message}")
                    continuation.resumeWithException(error)
                }
            }
        }
    }

    private fun getCreateRequestBody(type: String, validForDays: Int, multiplier: Int, category: String): RequestBody {
        val json = """
                {
                    "type": "$type",
                    "validForDays": $validForDays,
                    "multiplier": $multiplier,
                    "category": "$category"
                }
            """.trimIndent()
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())
        return body
    }
}