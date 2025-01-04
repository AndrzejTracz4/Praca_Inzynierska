package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Int,
    val name: String,
    val email: String,
    val enabled: Boolean,
    val playerLevel: Int,
    val playerExperience: Int,
    val roles: List<String>,
    val playerStatistics: PlayerStatistics,
    var userPhotoPath: String,
    val balance: Int
)