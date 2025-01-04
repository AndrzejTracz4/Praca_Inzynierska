package com.example.pracainynierska.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pracainynierska.API.handler.authorization.AuthorizationHandlerInterface
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.model.Booster
import com.example.pracainynierska.model.Task
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class BoosterViewModel(
    pc: PlayerContextInterface,
    private val playerAuthorizationHandler: AuthorizationHandlerInterface
) : AbstractViewModel(pc) {

    private val _boosters = MutableLiveData<List<Booster>>()
    val boosters: LiveData<List<Booster>> get() = _boosters

    fun getBoosters(): List<Booster> {
        return _boosters.value ?: emptyList()
    }

    fun addBooster(booster: Booster) {
        val currentBoosters = _boosters.value?.toMutableList() ?: mutableListOf()
        currentBoosters.add(booster)
        _boosters.value = currentBoosters
        Log.d("BoosterViewModel", "Dodano booster: $booster")
    }

}