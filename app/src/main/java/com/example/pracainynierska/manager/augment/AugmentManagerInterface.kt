package com.example.pracainynierska.manager.augment

import com.example.pracainynierska.API.model.Augment

interface AugmentManagerInterface {
    suspend fun addAugment(
        type : String,
        multiplier : Int,
        validForDays : Int,
        category : String
    ): Augment?

}
