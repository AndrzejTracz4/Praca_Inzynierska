package com.example.pracainynierska.manager.shop

import com.example.pracainynierska.API.model.Player

interface PurchaseHandlerInterface {
    fun canAfford(player: Player, price: Int): Boolean
    suspend fun handle(
        type : String,
        multiplier : Int,
        validForDays : Int,
        category : String,
    )
}