package com.example.pracainynierska.manager.daily_challenge

import androidx.lifecycle.LiveData
import com.example.pracainynierska.API.model.Challenge
import com.example.pracainynierska.API.model.Task

interface DailyChallengeManagerInterface {
    fun get(): LiveData<Challenge>
    fun checkStatus(task: Task?)
    fun getStatus(): LiveData<String>
    suspend fun load()
    suspend fun accept()
    suspend fun complete()
}