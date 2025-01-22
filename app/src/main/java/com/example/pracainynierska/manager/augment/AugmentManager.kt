package com.example.pracainynierska.manager.augment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pracainynierska.API.api_client.AugmentApi
import com.example.pracainynierska.API.model.Augment

class AugmentManager(
    private val apiClient: AugmentApi
) : AugmentManagerInterface {

    private val _boosters = MutableLiveData<List<Augment>>()
    val boosters: LiveData<List<Augment>> get() = _boosters


    override fun getAugments(): List<Augment> {
        return _boosters.value ?: emptyList()
    }

    override fun getAugmentsList(): LiveData<List<Augment>> {
        return _boosters
    }

    override fun addAugment(
        type: String,
        multiplier: Int,
        validForDays: Int,
        category: String
    ) {
        apiClient.addAugment(
            type,
            validForDays,
            multiplier,
            category
        )
    }
}
