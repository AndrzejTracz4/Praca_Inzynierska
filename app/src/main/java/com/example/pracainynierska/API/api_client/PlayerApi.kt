package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.model.Token
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.API.Exception.AuthorizationFailedException
import com.example.pracainynierska.API.model.Player
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.serialization.json.Json

class PlayerApi : ApiDetails() {
    private val loginCheckPath : String = "api/auth"

    private val getPlayerFromTokenPath : String = "api/player-from-token"

    fun authorize(email: String, password: String) {
        val body = getRequestBody(email, password)

        val request = Request
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
            throw AuthorizationFailedException();
        }

        getPlayerFromToken()
    }

    fun getPlayerFromToken() : Player {
        val playerRequest = Request
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

    private fun getRequestBody(email: String, password: String): RequestBody {
        val json = """
                {
                    "email": "$email",
                    "password": "$password"
                }
            """.trimIndent()
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())
        return body
    }
}