package com.example.pracainynierska.manager.shop

import com.example.pracainynierska.model.Augment

interface PurchaseHandlerInterface {
    fun canAfford(balance: Int, price: Int): Boolean
    fun handlePurchase(augment: Augment): Boolean
}