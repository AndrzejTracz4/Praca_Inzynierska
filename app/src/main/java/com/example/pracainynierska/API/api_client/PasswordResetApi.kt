package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.API.Exception.RequestFailedException
import com.example.pracainynierska.API.factory.RequestValidationExceptionFactory
import com.example.pracainynierska.API.model.EmptyResponse
import com.example.pracainynierska.API.model.TokenResponse
import com.example.pracainynierska.API.model.error_response.ValidationErrorResponse
import com.example.pracainynierska.context.PlayerContextInterface
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class PasswordResetApi(playerContext: PlayerContextInterface) : ApiDetails(playerContext) {

    private val getResetCodePath = "api/initialize-reset-password"

    private val verifyResetCodePath = "api/reset-password"

    private val changePasswordWithToken = "api/change-password"

    suspend fun sendResetCode(email: String) = suspendCancellableCoroutine<Unit> { continuation ->
        Log.d("ResetCode API", "Getting reset code for email: $email")

        val body = getResetCodeRequestBody(email)
        val request = Request.Builder()
            .url(buildPath(getResetCodePath))
            .post(body)
            .build()

        request(request, ListSerializer(EmptyResponse.serializer())) { result ->
            result.onSuccess {
                Log.d("ResetCode API Success", "Successfully retrieved reset code for email: $email")
                continuation.resume(Unit)
            }.onFailure { error ->
                Log.e("ResetCode API Failed", error.message ?: "Unknown error")
                continuation.resumeWithException(error)
            }
        }
    }

    suspend fun verifyResetCode(code: String): String = suspendCancellableCoroutine { continuation ->
        Log.d("ResetCode API", "Verifying reset code: $code")

        val request = Request.Builder()
            .url(buildPath("$verifyResetCodePath/$code"))
            .post("".toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        Log.d("ResetCode API", "Created request for verifying code")

        request(request, TokenResponse.serializer()) { result ->
            result.onSuccess { tokenResponse ->
                Log.d("ResetCode API", "Received token: ${tokenResponse.token}")
                continuation.resume(tokenResponse.token)
            }.onFailure { error ->
                Log.e("ResetCode API Failed", error.message ?: "Unknown error")
                continuation.resumeWithException(error)
            }
        }
    }

    suspend fun setNewPassword(password: String, token: String) = suspendCancellableCoroutine<Unit> { continuation ->
        Log.d("ResetCode API", "Setting new password")

        val body = getSetNewPasswordRequestBody(password)
        val request = Request.Builder()
            .url(buildPath("$changePasswordWithToken/$token"))
            .post(body)
            .build()

        request(request, ListSerializer(EmptyResponse.serializer())) { result ->
            result.onSuccess {
                Log.d("ResetCode API Success", "Successfully set new password")
                continuation.resume(Unit)
            }.onFailure { error ->
                Log.e("ResetCode API Failed", error.message ?: "Unknown error")
                continuation.resumeWithException(error)
            }
        }
    }

    private fun getResetCodeRequestBody(email: String): RequestBody {
        val json = """
                {
                    "email": "$email"
                }
            """.trimIndent()

        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    private fun getSetNewPasswordRequestBody(
        password: String,
    ): RequestBody {
        val json = """
                {
                    "password": "$password"
                }
            """.trimIndent()

        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

}
