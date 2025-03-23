package com.example.pracainynierska.manager.daily_challenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pracainynierska.API.api_client.DailyChallengeApi
import com.example.pracainynierska.API.model.Challenge
import com.example.pracainynierska.API.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DailyChallengeManager(
    private val apiClient: DailyChallengeApi
) : DailyChallengeManagerInterface {

    private val _dailyChallenge = MutableLiveData<Challenge>()
    private val dailyChallenge: LiveData<Challenge> = _dailyChallenge

    private val _status = MutableLiveData("unknown")
    private val status: LiveData<String> = _status

    override fun get(): LiveData<Challenge> {
        return dailyChallenge
    }

    override fun getStatus(): LiveData<String> {
        return status
    }

    override suspend fun load() {
        withContext(Dispatchers.IO) {
            try {
                val challenge = apiClient.getDailyChallenge()
                _dailyChallenge.postValue(challenge)
                Log.d("DailyChallengeManager", "Loaded challenge: $challenge")
            } catch (e: Exception) {
                Log.e("DailyChallengeManager", "Error loading challenge", e)
                throw e
            }
        }
    }

    override fun checkStatus(task: Task?) {
        when (task?.status) {
            "accepted" -> _status.postValue("accepted")
            "completed" -> _status.postValue("completed")
            else -> _status.postValue("unknown")
        }
    }

    override suspend fun accept() {
        withContext(Dispatchers.IO) {
            val accepted = apiClient.acceptDailyChallenge()
            if(accepted) {
                _status.postValue("accepted")
            } else {
                _status.postValue("unknown")
            }
        }
    }

    override suspend fun complete() {
        withContext(Dispatchers.IO) {
            val completed = apiClient.completeDailyChallenge()
            if(completed) {
                _status.postValue("completed")
            } else {
                _status.postValue("unknown")
            }
        }
    }
}
