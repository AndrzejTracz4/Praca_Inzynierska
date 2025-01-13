package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.API.Exception.AuthorizationFailedException
import com.example.pracainynierska.API.Exception.RequestFailedException
import com.example.pracainynierska.API.factory.RequestValidationExceptionFactory
import com.example.pracainynierska.API.model.Player
import com.example.pracainynierska.API.model.Token
import com.example.pracainynierska.API.model.error_response.ValidationErrorResponse
import com.example.pracainynierska.context.PlayerContextInterface
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class PlayerApi(
    playerContext: PlayerContextInterface,
) : ApiDetails(playerContext) {
    private val loginCheckPath: String = "api/auth"

    private val getPlayerFromTokenPath: String = "api/player-from-token"

    private val registerPath: String = "api/register"

    private val RequestValidationExceptionFactory = RequestValidationExceptionFactory()

    fun registerPlayer(
        username: String,
        email: String,
        password: String,
    ) {
        val body = getRegistrationRequestBody(username, email, password)
        Log.d("Player API", "Created body")

        val request =
            Request
                .Builder()
                .addHeader("Authorization", "Bearer ${this.getToken()}")
                .url(buildPath(registerPath))
                .post(body)
                .build()

        Log.d("Player API", "Created request")
        val response = apiClient.newCall(request).execute()

        Log.d("Player API", "Sent request")
        val responseBody = response.body?.string()
        Log.d("Login Check API", responseBody.toString())

        if (response.isSuccessful && responseBody != null) {
            Log.d("Registration API Success", responseBody.toString())
        }

        if (!response.isSuccessful) {
            val json = responseBody.toString()
            Log.d("Registration API Failed", json)
            val jsonBuilder = Json { ignoreUnknownKeys = true }
            if (422 == response.code) {
                val errorResponse = jsonBuilder.decodeFromString<ValidationErrorResponse>(json)
                val exception = RequestValidationExceptionFactory.create(errorResponse)
                throw exception
            }

            Log.d("Registration API Failed", response.code.toString())
            throw RequestFailedException(responseBody.toString())
        }
    }

    fun authorize(
        email: String,
        password: String,
    ) {
        val body = getLoginRequestBody(email, password)

        val request =
            Request
                .Builder()
                .url(buildPath(loginCheckPath))
                .post(body)
                .build()

        val response = apiClient.newCall(request).execute()
        val responseBody = response.body?.string()
        Log.d("Login Check API", responseBody.toString())

        if (response.isSuccessful && responseBody != null) {
            val tokenResponse = Json.decodeFromString<Token>(responseBody)
            this.setToken(tokenResponse.token)
        }

        if (!response.isSuccessful) {
            throw AuthorizationFailedException()
        }

        getPlayerFromToken()
    }

    fun getPlayerFromToken(): Player {
        val playerRequest =
            Request
                .Builder()
                .addHeader("Authorization", "Bearer ${this.getToken()}")
                .url(buildPath(getPlayerFromTokenPath))
                .get()
                .build()

        val playerResponse = apiClient.newCall(playerRequest).execute()
        val playerResponseBody = playerResponse.body?.string()
        Log.d("Player API", playerResponseBody.toString())

        if (playerResponse.isSuccessful && playerResponseBody != null) {
            val player = Json.decodeFromString<Player>(playerResponseBody)
            this.setPlayer(player)
        }

        return this.getPlayer()!!
    }

    private fun getLoginRequestBody(
        email: String,
        password: String,
    ): RequestBody {
        val json =
            """
            {
                "email": "$email",
                "password": "$password"
            }
            """.trimIndent()
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())
        return body
    }

    private fun getRegistrationRequestBody(
        name: String,
        email: String,
        password: String,
    ): RequestBody {
        val json =
            """
            {
                "name": "$name",
                "email": "$email",
                "password": "$password"
            }
            """.trimIndent()
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())
        return body
    }
}
