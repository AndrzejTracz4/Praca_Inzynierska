package com.example.pracainynierska.view_model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.dictionary.types.ShopTypes
import com.example.pracainynierska.manager.shop.CalculatorInterface
import com.example.pracainynierska.manager.shop.PurchaseHandlerInterface
import com.example.pracainynierska.model.Augment
import java.time.LocalDate

class ShopViewModel(
    pc: PlayerContextInterface,
    private val calculator: CalculatorInterface,
    private val purchaseHandler: PurchaseHandlerInterface
) : AbstractViewModel(pc) {

    fun calculateCost(
        shopMode: String,
        duration: Int,
        multiplier: Int
    ): Int {
        return calculator.calculateCost(shopMode, duration, multiplier)
    }

    fun checkIfCanAfford(price: Int): Boolean {
        val currentPlayer = playerContext.getPlayer() ?: return false
        return purchaseHandler.canAfford(currentPlayer.balance, price)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun buyBooster(
        shopMode: String,
        category: String,
        duration: Int,
        multiplier: Int,
        price: Int
    ): Boolean {

        if (!checkIfCanAfford(price)) {
            return false
        }

        val augment = Augment(
            shopMode = shopMode,
            category = category,
            multiplier = if (shopMode == ShopTypes.BOOSTER) (multiplier / 10) else 1,
            duration = (duration / 10),
            price = price,
            isActive = true,
            startDate = LocalDate.now()
        )

        return purchaseHandler.handlePurchase(augment)
    }
}