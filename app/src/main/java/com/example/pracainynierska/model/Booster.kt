package com.example.pracainynierska.model

import java.time.LocalDate
import java.util.Date

data class Booster(
    val id: Int,
    val shopMode: String,
    val category: String,
    val multiplier: Int,
    val duration: Int,
    val price: Int,
    var isActive: Boolean = false,
    val startDate: LocalDate
)
