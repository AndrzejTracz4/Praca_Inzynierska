package com.example.pracainynierska.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.Exception.RequestValidationException
import com.example.pracainynierska.API.model.Achievement
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.achievement.AchievementManagerInterface
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AchievementViewModel(
    pc: PlayerContextInterface,
    private val achievementManager: AchievementManagerInterface
) : AbstractViewModel(pc) {

    fun claimAchievement(id: Int, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("AchievementViewModel", "Claiming achievement")

                val claimedAchievement = achievementManager.claim(id)

                Log.d("AchievementViewModel", "Achievement claimed: $claimedAchievement")

                if (claimedAchievement) {
                    playerContext.claimPlayerAchievement(id)
                    onSuccess()
                } else {
                    onError()
                }
            } catch (e: RequestValidationException) {
                Log.e("AchievementViewModel", "ClaimingError - validation exception")
                onError()
            } catch (e: Exception) {
                Log.e("AchievementViewModel - Claiming failed", e.message.toString())
                onError()
            }
        }
    }
}