package com.example.pracainynierska.view_model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.dictionary.types.ShopTypes
import com.example.pracainynierska.manager.shop.CalculatorInterface
import com.example.pracainynierska.manager.shop.PurchaseHandlerInterface
import java.time.LocalDate

class ShopViewModel(
    pc: PlayerContextInterface,
    private val calculator: CalculatorInterface,
    private val purchaseHandler: PurchaseHandlerInterface
) : AbstractViewModel(pc) {

    fun calculateCost(
        type: String,
        validForDays: Int,
        multiplier: Int
    ): Int {
        return calculator.calculateCost(type, validForDays, multiplier)
    }

    fun checkIfCanAfford(price: Int): Boolean {
        val currentPlayer = playerContext.getPlayer() ?: return false
        return purchaseHandler.canAfford(currentPlayer.balance, price)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun buyBooster(
        type: String,
        categoryName: String,
        validForDays: Int,
        multiplier: Int,
        price: Int
    ): Boolean {

        if (!checkIfCanAfford(price)) {
            return false
        }

        val augment = AugmentModel(
            type = type,
            categoryName = "/api/categories/15",
            multiplier = if (type == ShopTypes.BOOSTER) (multiplier / 10) else 1,
            validForDays = (validForDays / 10),
            price = price,
            createdAt = LocalDate.now()
        )

        return purchaseHandler.handlePurchase(augment)
    }
}