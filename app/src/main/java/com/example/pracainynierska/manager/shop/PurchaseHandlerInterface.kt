package com.example.pracainynierska.manager.shop

interface PurchaseHandlerInterface {
    fun canAfford(balance: Int, price: Int): Boolean
    fun handlePurchase(augment: AugmentModel): Boolean
}