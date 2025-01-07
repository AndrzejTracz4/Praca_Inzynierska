package com.example.pracainynierska.manager.augment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.model.Augment
import com.example.pracainynierska.view_model.AbstractViewModel

class AugmentManager (
    pc: PlayerContextInterface,
) : AbstractViewModel(pc) {

    private val _boosters = MutableLiveData<List<Augment>>()
    val boosters: LiveData<List<Augment>> get() = _boosters

    fun getBoosters(): List<Augment> {
        return _boosters.value ?: emptyList()
    }

    fun addBooster(augment: Augment) {
        val currentBoosters = _boosters.value?.toMutableList() ?: mutableListOf()
        currentBoosters.add(augment)
        _boosters.value = currentBoosters
        Log.d("BoosterViewModel", "Dodano booster: $augment")
    }

}