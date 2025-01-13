package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.context.PlayerContextInterface
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AugmentApi(playerContext: PlayerContextInterface) : ApiDetails(playerContext) {
    private val createPath : String = "api/augments"

    fun addAugment(type: String, validForDays: Int, multiplier: Int, category: String) {
        val body = getCreateRequestBody(type, validForDays, multiplier, category)
        Log.d("Augment API", "Created body")

        return request(Request
            .Builder()
            .url(buildPath(createPath))
            .post(body)
            .build()
        )
    }

    private fun getCreateRequestBody(type: String, validForDays: Int, multiplier: Int, category: String): RequestBody {

        val json = """
                {
                    "type": "$type",
                    "validForDays": $validForDays,
                    "multiplier": $multiplier,
                    "category": "$category"
                }
            """.trimIndent()
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())
        return body
    }
}