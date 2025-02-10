package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable

@Serializable
data class Reward (
    val experience: Int,
    val coins: Int,
)