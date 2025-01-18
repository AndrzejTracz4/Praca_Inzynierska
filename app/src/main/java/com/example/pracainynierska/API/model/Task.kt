package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    val type: String,
    val name: String,
    val description: String,
    val category: String,
    val difficulty: String,
    val interval: Int,
    val measureUnit: String,
    val startDate: String,
    val endDate: String,
    val status: String
)