package com.example.pracainynierska.API.handler.authorization

import com.example.pracainynierska.API.model.Player


interface AuthorizationHandlerInterface {
    suspend fun authorize(email: String, password: String): Player?

    fun getCurrentPlayer(): Player?
}