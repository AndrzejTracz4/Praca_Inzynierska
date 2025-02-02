package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerStatistics (
    var statistics: MutableList<Statistic> = mutableListOf()
)
