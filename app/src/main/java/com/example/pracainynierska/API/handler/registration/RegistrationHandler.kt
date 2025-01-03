package com.example.pracainynierska.API.handler.registration

import com.example.pracainynierska.API.api_client.PlayerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegistrationHandler(private val playerApi: PlayerApi) : RegistrationHandlerInterface {
    override suspend fun handle(name: String, email: String, password: String) {
        return withContext(Dispatchers.IO) {
            playerApi.registerPlayer(name, email, password)
        }
    }
}
