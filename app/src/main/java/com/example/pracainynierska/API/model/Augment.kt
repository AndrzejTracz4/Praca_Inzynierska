package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable

@Serializable
data class Augment(
    val type: String,
    val price: Int,
    val createdAt: String,
    val validForDays: Int,
    val endsAt: String,
    val multiplier: Int,
    val category: Category? = null,
    val validUntil: String,
    val categoryName: String
)