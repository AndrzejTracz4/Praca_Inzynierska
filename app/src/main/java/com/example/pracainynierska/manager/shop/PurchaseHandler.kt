package com.example.pracainynierska.manager.shop

import com.example.pracainynierska.manager.augment.AugmentManager
import com.example.pracainynierska.model.Augment

class PurchaseHandler(
    private val augmentManager: AugmentManager
) : PurchaseHandlerInterface {

    override fun canAfford(balance: Int, price: Int): Boolean {
        return balance >= price
    }

    override fun handlePurchase(augment: Augment): Boolean {
        augmentManager.addBooster(augment)
        return true
    }
}