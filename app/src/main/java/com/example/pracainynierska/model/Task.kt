package com.example.pracainynierska.model

import com.example.pracainynierska.ui_view_components.components.TaskMode

data class Task(
    val id: Int,
    val name: String,
    val difficulty: String,
    val category: String,
    val startDate: String,
    val endDate: String,
    val interval: Int,
    val measureUnit: String,
    val mode: TaskMode,
    val status: String,
    val description: String
)
