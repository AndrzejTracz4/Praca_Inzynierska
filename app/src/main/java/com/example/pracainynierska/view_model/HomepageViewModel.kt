package com.example.pracainynierska.view_model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.Exception.RequestValidationException
import com.example.pracainynierska.API.model.Challenge
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.daily_challenge.DailyChallengeManager
import com.example.pracainynierska.manager.daily_challenge.DailyChallengeManagerInterface
import com.example.pracainynierska.resolver.PhotoResourceResolver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomepageViewModel(
    pc: PlayerContextInterface,
    val appContext: Context,
    private val dailyChallengeManager: DailyChallengeManagerInterface
) : AbstractViewModel(pc) {

    private val photoResourceResolver = PhotoResourceResolver()

    var userPhotoResId = mutableIntStateOf(0)

    fun getPhotoResId(file: String): Int {
        return photoResourceResolver.getResId(appContext, file)
    }

    fun getUserPhotoResId(): Int {
        val userPhotoPath = playerContext.getPlayer()?.userPhotoPath
        userPhotoResId.intValue = userPhotoPath?.takeIf { it.isNotBlank() }
            ?.let { getPhotoResId(it) } ?: 0

        return userPhotoResId.intValue
    }

    val dailyChallenge = dailyChallengeManager.get()

    val dailyChallengeStatus = dailyChallengeManager.getStatus()

    fun acceptDailyChallenge() {
        viewModelScope.launch {
            try {
                dailyChallengeManager.accept()
            } catch (e: RequestValidationException) {
                Log.e("HomepageViewModel", "Validation exception")
            } catch (e: Exception) {
                Log.e("HomepageViewModel - Failed", e.message.toString())
            }
        }
    }

    fun completeDailyChallenge() {
        viewModelScope.launch {
            try {
                dailyChallengeManager.complete()
            } catch (e: RequestValidationException) {
                Log.e("HomepageViewModel", "Validation exception")
            } catch (e: Exception) {
                Log.e("HomepageViewModel - Failed", e.message.toString())
            }
        }
    }
}
