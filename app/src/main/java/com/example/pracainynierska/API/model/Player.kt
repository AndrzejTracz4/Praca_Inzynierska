package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Int,
    val name: String,
    val email: String,
    val enabled: Boolean,
    val vacations: Boolean,
    val roles: List<String>,
    var playerLevel: Int,
    var playerExperience: Int,
    var completedTasks: Int,
    var userPhotoPath: String,
    var playerStatistics: PlayerStatistics,
    var categories: MutableList<Category> = mutableListOf(),
    var balance: Int,
    var achievements: MutableList<Achievement> = mutableListOf(),
    var activeAugments: MutableList<Augment> = mutableListOf()
)