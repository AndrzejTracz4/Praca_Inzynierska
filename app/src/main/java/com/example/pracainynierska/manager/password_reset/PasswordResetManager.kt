package com.example.pracainynierska.manager.password_reset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.pracainynierska.API.api_client.PasswordResetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PasswordResetManager(private val apiClient: PasswordResetApi) : PasswordResetManagerInterface {

    private var token by mutableStateOf("")

    override suspend fun send(email: String) {
        return withContext(Dispatchers.IO) {
            apiClient.sendResetCode(email)
        }
    }

    override suspend fun verify(code: String) = withContext(Dispatchers.IO) {
        token = apiClient.verifyResetCode(code)
    }

    override suspend fun changePassword(password: String) {
        return withContext(Dispatchers.IO) {
            apiClient.setNewPassword(password, token)
        }
    }
}