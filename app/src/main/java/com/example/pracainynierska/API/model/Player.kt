package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Int,
    val name: String,
    val email: String,
    val enabled: Boolean,
    val roles: List<String>,
    var playerLevel: Int,
    var playerExperience: Int,
    var userPhotoPath: String,
    var balance: Int,
    var playerStatistics: PlayerStatistics,
    var activeAugments: MutableList<Augment> = mutableListOf(),
    var categories: MutableList<Category> = mutableListOf()
)