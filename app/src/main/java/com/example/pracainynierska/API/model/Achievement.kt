package com.example.pracainynierska.API.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.AchievementStatus
import kotlinx.serialization.Serializable

@Serializable
data class Achievement(
    val id: Int,
    val type: String,
    val requiredValue: Float,
    val coins: Int,
    val experience: Int,
    val completed: Boolean
) {
    @Composable
    fun getName(): String {
        return when (type) {
            "tasks_completed" -> {
                when (requiredValue.toInt()) {
                    10 -> stringResource(R.string.achieve_10_tasks)
                    20 -> stringResource(R.string.achieve_20_tasks)
                    50 -> stringResource(R.string.achieve_50_tasks)
                    100 -> stringResource(R.string.achieve_100_tasks)
                    200 -> stringResource(R.string.achieve_200_tasks)
                    else -> stringResource(R.string.unknown_achievement)
                }
            }
            else -> stringResource(R.string.unknown_achievement)
        }
    }

    @Composable
    fun getDescription(): String {
        return when (type) {
            "tasks_completed" -> stringResource(R.string.complete_x_tasks, requiredValue.toInt())
            else -> stringResource(R.string.unknown_description)
        }
    }
}
