package com.example.pracainynierska.dictionary

enum class TaskUnit(val key: String, val displayName: String) {
    MINUTES("minutes", "Minuta"),
    HOURS("hours", "Godzina"),
    DAYS("days", "Dzień"),
    MONTHS("months", "Miesiąc");

    companion object {
        fun fromKey(key: String): TaskUnit? {
            return entries.find { it.key == key }
        }
    }
}
