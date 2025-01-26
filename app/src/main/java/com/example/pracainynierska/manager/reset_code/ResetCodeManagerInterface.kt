package com.example.pracainynierska.manager.reset_code

interface ResetCodeManagerInterface {
    suspend fun get(email: String)
    suspend fun verify(code: String)
    suspend fun changePassword(newPassword: String)
}