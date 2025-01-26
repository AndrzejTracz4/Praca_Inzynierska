package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.API.Exception.RequestFailedException
import com.example.pracainynierska.API.factory.RequestValidationExceptionFactory
import com.example.pracainynierska.API.model.error_response.ValidationErrorResponse
import com.example.pracainynierska.context.PlayerContextInterface
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ResetCodeApi(playerContext: PlayerContextInterface) : ApiDetails(playerContext) {

    private val verifyResetCodePath = "api/verify-reset-code"

    private val getResetCodePath = "api/get-reset-code"

    private val RequestValidationExceptionFactory = RequestValidationExceptionFactory()

    fun getResetCode(email: String) {
        Log.d("ResetCode API", "Getting reset code for email: $email")

        val body = getResetCodeRequestBody(email)

        val request = Request
            .Builder()
            .url(buildPath(getResetCodePath))
            .post(body)
            .build()

        val response = apiClient.newCall(request).execute()

        val responseBody = response.body?.string()
        Log.d("ResetCode API", responseBody.toString())

        if (!response.isSuccessful) {
            val json = responseBody.toString()
            Log.d("ResetCode API Failed", json)
            throw RequestFailedException(responseBody.toString())
        }

        Log.d("ResetCode API Success", "Successfully retrieved reset code for email: $email")
    }

    fun verifyResetCode(email: String, code: String) {
        val body = getVerifyResetCodeRequestBody(email, code)
        Log.d("ResetCode API", "Created body")

        val request = Request
            .Builder()
            .url(buildPath(verifyResetCodePath))
            .patch(body)
            .build()

        Log.d("ResetCode API", "Created request")
        val response = apiClient.newCall(request).execute()

        Log.d("ResetCode API", "Sent request")
        val responseBody = response.body?.string()
        Log.d("ResetCode API", responseBody.toString())

        if (!response.isSuccessful) {
            val json = responseBody.toString()
            Log.d("ResetCode API Failed", json)
            val jsonBuilder = Json { ignoreUnknownKeys = true }
            if (422 == response.code) {
                val errorResponse = jsonBuilder.decodeFromString<ValidationErrorResponse>(json)
                val exception = RequestValidationExceptionFactory.create(errorResponse)
                throw exception
            }

            Log.d("ResetCode API Failed", response.code.toString())
            throw RequestFailedException(responseBody.toString())
        }
    }

    fun setNewPassword(email: String, resetCode: String, newPassword: String) {
        Log.d("ResetCode API", "Setting new password for email: $email")

        val body = getSetNewPasswordRequestBody(email, resetCode, newPassword)

        val request = Request
            .Builder()
            .url(buildPath("api/set-new-password"))
            .post(body)
            .build()

        val response = apiClient.newCall(request).execute()

        val responseBody = response.body?.string()
        Log.d("ResetCode API", responseBody.toString())

        if (!response.isSuccessful) {
            val json = responseBody.toString()
            Log.d("ResetCode API Failed", json)
            throw RequestFailedException(responseBody.toString())
        }

        Log.d("ResetCode API Success", "Successfully set new password for email: $email")
    }

    private fun getResetCodeRequestBody(email: String): RequestBody {
        val json = """
                {
                    "email": "$email"
                }
            """.trimIndent()

        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    private fun getVerifyResetCodeRequestBody(email: String, code: String): RequestBody {
        val json = """
                {
                    "email" : "$email",
                    "reset-code": "$code"
                }
            """.trimIndent()

        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    private fun getSetNewPasswordRequestBody(
        email: String,
        resetCode: String,
        newPassword: String
    ): RequestBody {
        val json = """
                {
                    "email": "$email",
                    "reset-code": "$resetCode",
                    "new-password": "$newPassword"
                }
            """.trimIndent()

        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

}
