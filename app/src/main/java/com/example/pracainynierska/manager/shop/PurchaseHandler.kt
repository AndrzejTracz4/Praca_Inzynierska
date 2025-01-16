package com.example.pracainynierska.manager.shop

import com.example.pracainynierska.API.model.Player
import com.example.pracainynierska.manager.augment.AugmentManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PurchaseHandler(
    private val augmentManager: AugmentManager
) : PurchaseHandlerInterface {

    override fun canAfford(player: Player, price: Int): Boolean {
        return player.balance >= price
    }

    override suspend fun handle(
        type : String,
        multiplier : Int,
        validForDays : Int,
        category : String,
    ) {
        return withContext(Dispatchers.IO) {
            augmentManager.addAugment(
                type = type,
                multiplier = multiplier,
                validForDays = validForDays,
                category = category
            )
        }
    }
}