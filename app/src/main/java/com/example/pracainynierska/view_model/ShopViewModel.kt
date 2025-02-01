package com.example.pracainynierska.view_model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.dictionary.types.AugmentTypes
import com.example.pracainynierska.manager.shop.CalculatorInterface
import com.example.pracainynierska.manager.shop.PurchaseHandlerInterface
import kotlinx.coroutines.launch

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
        return purchaseHandler.canAfford(currentPlayer, price)
    }

    private fun calculateNewBalance(price: Int): Int? {
        val currentPlayer = playerContext.getPlayer() ?: return null
        return calculator.calculateNewBalance(currentPlayer, price)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun buyBooster(
        type: String,
        validForDays: Int,
        multiplier: Int,
        category: String,
        price: Int,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        if (!checkIfCanAfford(price)) {
            throw Exception("Insufficient funds")
            //todo add insufficient funds message
        }

        viewModelScope.launch {
            val newAugment = purchaseHandler.handle(
                type = type,
                category = category,
                multiplier = if (type == AugmentTypes.BOOSTER) (multiplier / 10) else 2,
                validForDays = validForDays,
            )

            if(newAugment != null) {
                playerContext.addPlayerAugment(newAugment)
                val balance = calculateNewBalance(price)
                if(balance != null) {
                    playerContext.setPlayerBalance(balance)
                }
                onSuccess()
            } else {
                onError()
            }
        }
    }
}