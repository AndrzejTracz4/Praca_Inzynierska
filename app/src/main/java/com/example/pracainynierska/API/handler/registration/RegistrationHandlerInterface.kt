package com.example.pracainynierska.API.handler.registration

interface RegistrationHandlerInterface {
    suspend fun handle(
        name: String,
        email: String,
        password: String,
    )
}
