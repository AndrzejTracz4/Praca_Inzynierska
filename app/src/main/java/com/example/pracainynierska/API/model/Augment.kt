package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable

@Serializable
data class Augment(
    val type: String,
    val price: Int,
    val createdAt: String,
    val validForDays: Int,
    val multiplier: Int,
    val categoryName: String,
    val category: Category,
    val validUntil: String
)