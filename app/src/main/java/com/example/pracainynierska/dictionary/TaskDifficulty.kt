package com.example.pracainynierska.dictionary

import androidx.compose.ui.graphics.Color
import com.example.pracainynierska.R

enum class TaskDifficulty(val displayName: String, val iconResId: Int, val color: Color) {
    EASY("Łatwy", R.drawable.water, Color(0xFF3CB043)),
    MEDIUM("Średni", R.drawable.leaf, Color(0xFFFFFF00)),
    HARD("Trudny", R.drawable.flame, Color(0xFFFF0000));

    companion object {
        fun fromDisplayName(name: String): TaskDifficulty? {
            return entries.find { it.displayName == name }
        }
    }
}
