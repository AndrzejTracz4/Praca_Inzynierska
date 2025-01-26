package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String,
    val statistics: List<Statistics>
)
