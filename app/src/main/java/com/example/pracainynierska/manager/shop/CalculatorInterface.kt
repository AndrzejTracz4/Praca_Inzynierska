package com.example.pracainynierska.manager.shop

interface CalculatorInterface {
    fun calculateCost(
        shopMode: String,
        duration: Int,
        multiplier: Int,
    ): Int
}
