package com.example.pracainynierska.manager.augment

import androidx.lifecycle.LiveData
import com.example.pracainynierska.API.model.Augment

interface AugmentManagerInterface {
    fun addAugment(
        type : String,
        multiplier : Int,
        validForDays : Int,
        category : String
    )

    fun getAugments(): List<Augment>

    fun getAugmentsList(): LiveData<List<Augment>>
}
