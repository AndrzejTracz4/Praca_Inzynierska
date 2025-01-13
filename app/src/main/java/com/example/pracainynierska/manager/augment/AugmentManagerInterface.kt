package com.example.pracainynierska.manager.augment

import androidx.lifecycle.LiveData
import com.example.pracainynierska.API.model.Augment

interface AugmentManagerInterface {
    suspend fun addAugmentAPI(type: String, validForDays: Int, multiplier: Int, category: String)

    fun addAugment(augment: AugmentModel)

    fun getAugments(): List<Augment>

    fun getAugmentsList(): LiveData<List<Augment>>
}
