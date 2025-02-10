package com.example.pracainynierska.dictionary.types

import com.example.pracainynierska.R

enum class TaskType(val key: String, val displayName: String, val iconResId: Int) {
    RECURRING("recurring", "Cykliczne", R.drawable.repeat),
    ONE_TIME("one_time", "Jednorazowe", R.drawable.repeat_single),
    CHALLENGE("challenge", "Wyzwanie", R.drawable.daily_task);

    companion object {
        fun fromKey(key: String): TaskType? {
            return entries.find { it.key == key }
        }
    }
}
