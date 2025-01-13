package com.example.pracainynierska.manager.shop

import com.example.pracainynierska.manager.augment.AugmentManager

class PurchaseHandler(
    private val augmentManager: AugmentManager
) : PurchaseHandlerInterface {

    override fun canAfford(balance: Int, price: Int): Boolean {
        return balance >= price
    }

    override fun handlePurchase(augment: AugmentModel): Boolean {
        augmentManager.addAugment(augment)
        return true
    }
}