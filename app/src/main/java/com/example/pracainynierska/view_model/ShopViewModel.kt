package com.example.pracainynierska.view_model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.dictionary.types.ShopTypes
import com.example.pracainynierska.manager.shop.CalculatorInterface
import com.example.pracainynierska.manager.shop.PurchaseHandlerInterface
import kotlinx.coroutines.launch

class ShopViewModel(
    pc: PlayerContextInterface,
    private val calculator: CalculatorInterface,
    private val purchaseHandler: PurchaseHandlerInterface,
) : AbstractViewModel(pc) {
    fun calculateCost(
        type: String,
        validForDays: Int,
        multiplier: Int,
    ): Int = calculator.calculateCost(type, validForDays, multiplier)

    fun checkIfCanAfford(price: Int): Boolean {
        val currentPlayer = playerContext.getPlayer() ?: return false
        return purchaseHandler.canAfford(currentPlayer, price)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun buyBooster(
        type: String,
        validForDays: Int,
        multiplier: Int,
        category: String,
        price: Int,
    ) {
        if (!checkIfCanAfford(price)) {
            throw Exception("Insufficient funds")
            // todo add insufficient funds message
        }
        viewModelScope.launch {
            purchaseHandler.handle(
                type = type,
                category = category,
                multiplier = if (type == ShopTypes.BOOSTER) (multiplier / 10) else 2,
                validForDays = validForDays,
            )
        }
    }
}
