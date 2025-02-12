package com.example.pracainynierska.manager.password_reset

interface PasswordResetManagerInterface {
    suspend fun send(email: String)
    suspend fun verify(code: String)
    suspend fun changePassword(password: String)
}