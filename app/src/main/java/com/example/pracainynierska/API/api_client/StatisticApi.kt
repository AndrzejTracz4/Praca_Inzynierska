package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.context.PlayerContextInterface
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class StatisticApi(playerContext: PlayerContextInterface) : ApiDetails(playerContext) {
    private val createPath : String = "api/statistics"

    fun addStatistic(name: String, iconPath: String) {
        val body = getCreateRequestBody(name, iconPath)
        Log.d("Statistic API", "Created body")

        return request(
            Request
            .Builder()
            .url(buildPath(createPath))
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .post(body)
            .build()
        )
    }

    private fun getCreateRequestBody(name: String, iconPath: String): RequestBody {
        val json = """
                {
                    "name": "$name",
                    "iconPath": "$iconPath"
                }
            """.trimIndent()
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())
        return body
    }

}