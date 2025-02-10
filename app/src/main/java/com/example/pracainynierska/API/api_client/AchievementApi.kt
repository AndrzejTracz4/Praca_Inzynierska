package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.API.model.Achievement
import com.example.pracainynierska.context.PlayerContextInterface
import kotlinx.serialization.builtins.ListSerializer
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AchievementApi(playerContext: PlayerContextInterface) : ApiDetails(playerContext) {
    private val achievementPath: String = "api/achievements"

    suspend fun claimAchievement(id: Int): Boolean {
        Log.d("Achievement API", "Created body")

        val achievementsRequest = Request
            .Builder()
            .url(buildPath("$achievementPath/$id/complete"))
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .patch("".toRequestBody("application/merge-patch+json".toMediaTypeOrNull()))
            .build()

        return suspendCoroutine { continuation ->
            request(achievementsRequest, ListSerializer(Achievement.serializer())) { result ->
                result.onSuccess { _ ->
                    Log.d("Achievement API", "Achievement claimed successfully")
                    continuation.resume(true)
                }.onFailure { error ->
                    Log.e("Achievement API", "Error: ${error.message}")
                    continuation.resume(false)
                }
            }
        }
    }

}