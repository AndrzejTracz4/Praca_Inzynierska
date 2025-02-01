package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.API.model.Statistic
import com.example.pracainynierska.context.PlayerContextInterface
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class StatisticApi(playerContext: PlayerContextInterface) : ApiDetails(playerContext) {
    private val statisticPath : String = "api/statistics"

    suspend fun addStatistic(name: String, iconPath: String): Statistic? {
        val body = getCreateRequestBody(name, iconPath)
        Log.d("Statistic API", "Created body")

        val statisticRequest = Request
            .Builder()
            .url(buildPath(statisticPath))
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .post(body)
            .build()

        return suspendCoroutine { continuation ->
            request(statisticRequest, Statistic.serializer()) { result ->
                result.onSuccess { statistic ->
                    Log.d("Statistic API", "Received statistic: $statistic")
                    continuation.resume(statistic)
                }.onFailure { error ->
                    Log.e("Statistic API", "Error: ${error.message}")
                    continuation.resumeWithException(error)
                }
            }
        }

    }

    suspend fun editStatistic(id: Int, name: String, iconPath: String): Statistic? {
        val body = getUpdateRequestBody(name, iconPath)
        Log.d("Statistic API", "Created body")

        val statisticRequest = Request
            .Builder()
            .url(buildPath("$statisticPath/$id"))
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .patch(body)
            .build()

        return suspendCoroutine { continuation ->
            request(statisticRequest, Statistic.serializer()) { result ->
                result.onSuccess { statistic ->
                    Log.d("Statistic API", "Received statistic: $statistic")
                    continuation.resume(statistic)
                }.onFailure { error ->
                    Log.e("Statistic API", "Error: ${error.message}")
                    continuation.resumeWithException(error)
                }
            }
        }
    }

    suspend fun deleteStatistic(id: Int): Boolean {
        val statisticRequest = Request
            .Builder()
            .url(buildPath("$statisticPath/$id"))
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .delete()
            .build()

        return suspendCoroutine { continuation ->
            request(statisticRequest, Statistic.serializer()) { result ->
                result.onSuccess {
                    Log.d("Statistic API", "Statistic deleted successfully")
                    continuation.resume(true)
                }.onFailure { error ->
                    if (error.message == "204") {
                        Log.d("Statistic API", "Statistic deleted successfully (204 No Content)")
                        continuation.resume(true)
                    } else {
                        Log.e("Statistic API", "Error: ${error.message}")
                        continuation.resume(false)
                    }
                }
            }
        }
    }

    private fun getCreateRequestBody(name: String, iconPath: String): RequestBody {
        val json = """
                {
                    "name": "$name",
                    "iconPath": "$iconPath"
                }
            """.trimIndent()
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())
        return body
    }

    private fun getUpdateRequestBody(name: String, iconPath: String): RequestBody {
        val json = """
                {
                    "name": "$name",
                    "iconPath": "$iconPath"
                }
            """.trimIndent()
        val body = json.toRequestBody("application/merge-patch+json".toMediaTypeOrNull())
        return body
    }

}