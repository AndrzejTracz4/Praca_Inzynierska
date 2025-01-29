package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.context.PlayerContextInterface
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class CategoryApi(playerContext: PlayerContextInterface) : ApiDetails(playerContext) {
    private val categoryPath : String = "api/categories"

    fun addCategory(name: String, statisticsIds: ArrayList<String>) {
        val body = getCreateRequestBody(name, statisticsIds)
        Log.d("Category API", "Created body")

        return request(Request
            .Builder()
            .url(buildPath(categoryPath))
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .post(body)
            .build()
        )
    }

    fun editCategory(id: Int, name: String, statisticsIds: ArrayList<String>) {
        val body = getUpdateRequestBody(name, statisticsIds)
        Log.d("Category API", "Created body")

        return request(Request
            .Builder()
            .url(buildPath("$categoryPath/$id"))
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .patch(body)
            .build()
        )
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