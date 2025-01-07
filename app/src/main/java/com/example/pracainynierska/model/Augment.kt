package com.example.pracainynierska.model

import java.time.LocalDate

data class Augment(
    val id: Int? = null,
    val shopMode: String,
    val category: String,
    val multiplier: Int,
    val duration: Int,
    val price: Int,
    var isActive: Boolean = false,
    val startDate: LocalDate
)
