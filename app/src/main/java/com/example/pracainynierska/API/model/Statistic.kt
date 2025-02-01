package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable


@Serializable
data class Statistic(
    val id: Int,
    val name: String,
    val iconPath: String,
    var experience: Int? = 0,
    var level: Int? = 1,
)
