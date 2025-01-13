package com.example.pracainynierska.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.augment.AugmentManager
import com.example.pracainynierska.manager.shop.Calculator
import com.example.pracainynierska.manager.shop.PurchaseHandler

class ShopViewModelFactory(
    private val playerContext: PlayerContextInterface,
    private val augmentManager: AugmentManager,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopViewModel::class.java)) {
            val calculator = Calculator()
            val purchaseHandler = PurchaseHandler(augmentManager)

            return ShopViewModel(playerContext, calculator, purchaseHandler) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
