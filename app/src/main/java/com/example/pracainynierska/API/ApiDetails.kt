package com.example.pracainynierska.API

import android.util.Log
import com.example.pracainynierska.API.Exception.RequestFailedException
import com.example.pracainynierska.API.model.Player
import com.example.pracainynierska.API.model.error_response.ValidationErrorResponse
import com.example.pracainynierska.context.PlayerContextInterface
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import com.example.pracainynierska.API.factory.RequestValidationExceptionFactory

open class ApiDetails(
    private var playerContext: PlayerContextInterface,
) {
    private val apiPath: String = "http://46.101.212.73"

    protected val apiClient = OkHttpClient()

    private val RequestValidationExceptionFactory = RequestValidationExceptionFactory()

    fun request(request: Request) {
        Log.d("Category API", "Created request")
        val response = apiClient.newCall(request).execute()

        Log.d("Category API", "Sent request")
        val responseBody = response.body?.string()
        Log.d("Category API", responseBody.toString())

        if (response.isSuccessful && responseBody != null) {
            Log.d("Category API Success", responseBody.toString())
        }

        if (!response.isSuccessful) {
            val json = responseBody.toString()
            Log.d("Category API Failed", json)
            val jsonBuilder = Json { ignoreUnknownKeys = true }
            if (422 == response.code) {
                val errorResponse = jsonBuilder.decodeFromString<ValidationErrorResponse>(json)
                val exception = RequestValidationExceptionFactory.create(errorResponse)
                throw exception
            }

            Log.d("Category API Failed", response.code.toString())
            throw RequestFailedException(responseBody.toString())
        }
    }

    fun reinitializeApiClient() {
        val token = getToken()
        apiClient.newBuilder().addInterceptor { chain ->
            val request =
                chain
                    .request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            chain.proceed(request)
        }
    }

    fun getApiPath(): String = apiPath

    fun getToken(): String? = playerContext.getToken()

    fun setToken(token: String) {
        this.playerContext.setToken(token)
        reinitializeApiClient()
    }

    fun getPlayer(): Player? = playerContext.getPlayer()

    fun setPlayer(player: Player) {
        this.playerContext.setPlayer(player)
    }

    fun buildPath(path: String): String = "$apiPath/$path"

    fun authorized(): Boolean = getToken() != null
}
