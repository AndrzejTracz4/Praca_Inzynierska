package com.example.pracainynierska.API

import android.util.Log
import com.example.pracainynierska.API.Exception.RequestFailedException
import com.example.pracainynierska.API.model.Player
import com.example.pracainynierska.context.PlayerContextInterface
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


open class ApiDetails(private var playerContext: PlayerContextInterface) {
    private val apiPath: String = ""

    protected val apiClient = OkHttpClient()

    fun <T> request(request: Request, responseType: DeserializationStrategy<T>, callback: (Result<T>) -> Unit) {
        Log.d("API Details", "Created request")

        apiClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("API Details", "Request failed: ${e.message}")
                callback(Result.failure(e))
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val responseBody = response.body?.string()
                    Log.d("API Details", "Response: $responseBody")

                    if (!response.isSuccessful || responseBody.isNullOrEmpty()) {
                        val error = RequestFailedException(response.code.toString())
                        callback(Result.failure(error))
                        return
                    }

                    try {
                        val jsonBuilder = Json { ignoreUnknownKeys = true }
                        val result = jsonBuilder.decodeFromString(responseType, responseBody)
                        callback(Result.success(result))
                    } catch (e: Exception) {
                        callback(Result.failure(e))
                    }
                }
            }
        })
    }


    fun reinitializeApiClient() {
        val token = getToken()
        apiClient.newBuilder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        }
    }

    fun getApiPath(): String {
        return apiPath
    }

    fun getToken(): String? {
        return playerContext.getToken()
    }

    fun setToken(token: String) {
        this.playerContext.setToken(token)
        reinitializeApiClient()
    }

    fun getPlayer(): Player? {
        return playerContext.getPlayer()
    }

    fun setPlayer(player: Player) {
        this.playerContext.setPlayer(player)
    }

    fun buildPath(path: String): String {
        return "$apiPath/$path"
    }

    fun authorized(): Boolean {
        return getToken() != null
    }
}