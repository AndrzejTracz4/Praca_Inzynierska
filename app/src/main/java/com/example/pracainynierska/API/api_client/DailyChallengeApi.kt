package com.example.pracainynierska.API.api_client

import android.util.Log
import com.example.pracainynierska.API.ApiDetails
import com.example.pracainynierska.API.model.Challenge
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.context.PlayerContextInterface
import kotlinx.serialization.builtins.ListSerializer
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DailyChallengeApi(playerContext: PlayerContextInterface) : ApiDetails(playerContext) {
    private val dailyChallengePath: String = "api/daily_challenge"

    suspend fun getDailyChallenge(): Challenge {
        val challengeRequest = Request
            .Builder()
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .url(buildPath(dailyChallengePath))
            .get()
            .build()

        Log.d("DailyChallenge API", "Getting daily challenge")

        return suspendCoroutine { continuation ->
            request(challengeRequest, Challenge.serializer()) { result ->
                result.onSuccess { challenge ->
                    Log.d("DailyChallenge API", "Received daily challenge: $challenge")
                    continuation.resume(challenge)
                }.onFailure { error ->
                    Log.e("DailyChallenge API", "Error: ${error.message}")
                    continuation.resumeWithException(error)
                }
            }
        }
    }

    suspend fun acceptDailyChallenge(): Boolean {
        val challengeRequest = Request
            .Builder()
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .url(buildPath("$dailyChallengePath/accept"))
            .post("".toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        Log.d("DailyChallenge API", "Accepting daily challenge")

        return suspendCoroutine { continuation ->
            request(challengeRequest, ListSerializer(Challenge.serializer())) { result ->
                result.onSuccess { _ ->
                    Log.d("DailyChallenge API", "Accepted daily challenge")
                    continuation.resume(true)
                }.onFailure { error ->
                    Log.e("DailyChallenge API", "Error: ${error.message}")
                    continuation.resume(false)
                }
            }
        }
    }

    suspend fun completeDailyChallenge(): Boolean {
        val challengeRequest = Request
            .Builder()
            .addHeader("Authorization", "Bearer ${this.getToken()}")
            .url(buildPath("$dailyChallengePath/complete"))
            .post("".toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        Log.d("DailyChallenge API", "Accepting daily challenge")

        return suspendCoroutine { continuation ->
            request(challengeRequest, ListSerializer(Challenge.serializer())) { result ->
                result.onSuccess { _ ->
                    Log.d("DailyChallenge API", "Competing daily challenge")
                    continuation.resume(true)
                }.onFailure { error ->
                    Log.e("DailyChallenge API", "Error: ${error.message}")
                    continuation.resume(false)
                }
            }
        }
    }

}