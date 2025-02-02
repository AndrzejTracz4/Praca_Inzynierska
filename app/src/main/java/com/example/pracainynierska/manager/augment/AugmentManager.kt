package com.example.pracainynierska.manager.augment

import com.example.pracainynierska.API.api_client.AugmentApi
import com.example.pracainynierska.API.model.Augment

class AugmentManager(
    private val apiClient: AugmentApi
) : AugmentManagerInterface {

    override suspend fun addAugment(
        type: String,
        multiplier: Int,
        validForDays: Int,
        category: String
    ) : Augment? {
        val result = apiClient.addAugment(
            type,
            validForDays,
            multiplier,
            category
        )
        return result
    }
}
