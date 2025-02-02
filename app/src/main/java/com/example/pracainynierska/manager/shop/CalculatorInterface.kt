package com.example.pracainynierska.manager.shop

import com.example.pracainynierska.API.model.Player

interface CalculatorInterface {
    fun calculateCost(
        shopMode: String,
        duration: Int,
        multiplier: Int
    ): Int

    fun calculateNewBalance(currentPlayer: Player, price: Int): Int
}