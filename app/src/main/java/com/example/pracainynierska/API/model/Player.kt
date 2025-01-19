package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Int,
    val name: String,
    val email: String,
    val enabled: Boolean,
    val roles: List<String>,
    val playerLevel: Int,
    val playerExperience: Int,
    var userPhotoPath: String,
    val balance: Int,
    val playerStatistics: PlayerStatistics,
    val activeAugments: List<Augment>,
    val categories: List<Category>
)