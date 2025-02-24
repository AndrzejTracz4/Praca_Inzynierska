package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    val type: String,
    val name: String,
    val description: String,
    val category: Category? = null,
    val difficulty: String,
    val status: String,
    val reward: Reward? = null,
    val createdAt: String,
    val startsAt: String,
    val endsAt: String,
    val completedAt: String? = null,
    val measureUnit: String = "hours",
    val interval: Int = 1,
)