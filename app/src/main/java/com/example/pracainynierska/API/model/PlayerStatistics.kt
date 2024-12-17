package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerStatistics (
    val statistics: List<Statistics>?
)
