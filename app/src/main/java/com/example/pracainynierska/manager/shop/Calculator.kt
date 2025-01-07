package com.example.pracainynierska.manager.shop

import com.example.pracainynierska.dictionary.types.ShopTypes

class Calculator : CalculatorInterface {
    override fun calculateCost(
        shopMode: String,
        duration: Int,
        multiplier: Int
    ): Int {
        return when (shopMode) {
            ShopTypes.SHIELD -> (duration / 10) * 2
            ShopTypes.BOOSTER -> (duration / 10) * 2 * (multiplier / 10)
            else -> 0
        }
    }
}