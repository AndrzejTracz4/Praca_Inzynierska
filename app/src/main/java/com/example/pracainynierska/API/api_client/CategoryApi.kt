package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.API.model.Category
import com.example.pracainynierska.context.PlayerContextInterface
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CategoryApi(playerContext: PlayerContextInterface) : ApiDetails(playerContext) {
    private val categoryPath: String = "api/categories"

    suspend fun addCategory(name: String, statisticsIds: ArrayList<String>): Category? {
        val body = getCreateRequestBody(name, statisticsIds)
        Log.d("Category API", "Created body")

        val categoryRequest = Request
            .Builder()
            .url(buildPath(categoryPath))
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .post(body)
            .build()

        return suspendCoroutine { continuation ->
            request(categoryRequest, Category.serializer()) { result ->
                result.onSuccess { category ->
                    Log.d("Category API", "Received category: $category")
                    continuation.resume(category)
                }.onFailure { error ->
                    Log.e("Category API", "Error: ${error.message}")
                    continuation.resumeWithException(error)
                }
            }
        }
    }

    suspend fun editCategory(id: Int, name: String, statisticsIds: ArrayList<String>): Category? {
        val body = getUpdateRequestBody(name, statisticsIds)
        Log.d("Category API", "Created body")

        val categoryRequest = Request
            .Builder()
            .url(buildPath("$categoryPath/$id"))
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .patch(body)
            .build()

        return suspendCoroutine { continuation ->
            request(categoryRequest, Category.serializer()) { result ->
                result.onSuccess { category ->
                    Log.d("Category API", "Received category: $category")
                    continuation.resume(category)
                }.onFailure { error ->
                    Log.e("Category API", "Error: ${error.message}")
                    continuation.resumeWithException(error)
                }
            }
        }
    }

    suspend fun deleteCategory(id: Int): Boolean {
        val categoryRequest = Request
            .Builder()
            .url(buildPath("$categoryPath/$id"))
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .delete()
            .build()

        return suspendCoroutine { continuation ->
            request(categoryRequest, Category.serializer()) { result ->
                result.onSuccess {
                    Log.d("Category API", "Category deleted successfully")
                    continuation.resume(true)
                }.onFailure { error ->
                    val statusCode = error.message?.toIntOrNull()
                    if (statusCode == 204) {
                        Log.d("Category API", "Category deleted successfully (204 No Content)")
                        continuation.resume(true)
                    } else {
                        Log.e("Category API", "Error: ${error.message}")
                        continuation.resume(false)
                    }
                }
            }
        }
    }

    private fun getCreateRequestBody(name: String, statisticsIds: ArrayList<String>): RequestBody {
        val json = """
                {
                    "name": "$name",
                    "statisticsIds": $statisticsIds
                }
            """.trimIndent()
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())
        return body
    }

    private fun getUpdateRequestBody(name: String, statisticsIds: ArrayList<String>): RequestBody {
        val json = """
                {
                    "name": "$name",
                    "statisticsIds": $statisticsIds
                }
            """.trimIndent()
        val body = json.toRequestBody("application/merge-patch+json".toMediaTypeOrNull())
        return body
    }
}