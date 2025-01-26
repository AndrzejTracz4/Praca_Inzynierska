package com.example.pracainynierska.manager.reset_code

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.pracainynierska.API.api_client.ResetCodeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResetCodeManager(private val apiClient: ResetCodeApi) : ResetCodeManagerInterface {

    var email by mutableStateOf("")
        private set

    var code by mutableStateOf("")
        private set

    override suspend fun get(email: String) {
        this.email = email
        return withContext(Dispatchers.IO) {
            apiClient.getResetCode(email)
        }
    }

    override suspend fun verify(code: String) {
        this.code = code
        return withContext(Dispatchers.IO) {
            apiClient.verifyResetCode(email, code)
        }
    }

    override suspend fun changePassword(newPassword: String) {
        return withContext(Dispatchers.IO) {
            apiClient.setNewPassword(email, code, newPassword)
        }
    }
}