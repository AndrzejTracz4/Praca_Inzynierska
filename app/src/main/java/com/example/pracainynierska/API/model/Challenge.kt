package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable

@Serializable
data class Challenge (
    val id: Int,
    val name: String,
    val description: String,
    val points: Int,
    val coins: Int
)