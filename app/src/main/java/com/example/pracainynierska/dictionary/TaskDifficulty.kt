package com.example.pracainynierska.dictionary

import androidx.compose.ui.graphics.Color
import com.example.pracainynierska.R

enum class TaskDifficulty(val key: String, val displayName: String, val iconResId: Int, val color: Color) {
    EASY("easy","Łatwy", R.drawable.water, Color(0xFF3CB043)),
    MEDIUM("medium","Średni", R.drawable.leaf, Color(0xFFFFFF00)),
    HARD("hard","Trudny", R.drawable.flame, Color(0xFFFF0000));

    companion object {
        fun fromKey(key: String): TaskDifficulty? {
            return entries.find { it.key == key }
        }
    }
}
