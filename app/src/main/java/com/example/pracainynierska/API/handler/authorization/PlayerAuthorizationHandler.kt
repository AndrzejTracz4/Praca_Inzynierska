package com.example.pracainynierska.API.handler.authorization

import android.util.Log
import com.example.pracainynierska.API.Exception.AuthorizationFailedException
import com.example.pracainynierska.API.api_client.PlayerApi
import com.example.pracainynierska.API.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerAuthorizationHandler : AuthorizationHandlerInterface {
    private val playerApi = PlayerApi()

    override suspend fun authorize(email: String, password: String): Player? {
        return withContext(Dispatchers.IO) {
            val playerApi = PlayerApi()
            try {
                if (playerApi.authorized()) {
                    return@withContext playerApi.getPlayer()
                }
                playerApi.authorize(email, password)
                return@withContext playerApi.getPlayer()
            } catch (e: AuthorizationFailedException) {
                Log.e("PlayerAuthorizationFailed", e.message.toString())
                return@withContext null

            } catch (e: Exception) {
                Log.e("PlayerAuthorization::authorize", e.message.toString())
                return@withContext null
            }
        }
    }

    override fun getCurrentPlayer(): Player? {
        return playerApi.getPlayer()
    }
}