package com.example.pracainynierska.manager.shop

import com.example.pracainynierska.API.model.Player
import com.example.pracainynierska.dictionary.types.AugmentTypes

class Calculator : CalculatorInterface {
    override fun calculateCost(
        shopMode: String,
        duration: Int,
        multiplier: Int
    ): Int {
        return when (shopMode) {
            AugmentTypes.SHIELD -> (duration / 10) * 2
            AugmentTypes.BOOSTER -> (duration / 10) * 2 * (multiplier / 10)
            else -> 0
        }
    }
    override fun calculateNewBalance(currentPlayer: Player, price: Int): Int {
        return currentPlayer.balance - price
    }
}