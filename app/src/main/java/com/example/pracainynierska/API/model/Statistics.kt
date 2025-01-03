package com.example.pracainynierska.API.model

import kotlinx.serialization.Serializable


@Serializable
data class Statistics(
    val code: String,
    val name: String,
    val value: Int,
    val level: Int
)
