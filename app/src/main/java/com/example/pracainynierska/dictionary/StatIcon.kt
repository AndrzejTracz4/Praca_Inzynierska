package com.example.pracainynierska.dictionary

import com.example.pracainynierska.R

enum class StatIcon(val rawResId: Int, val iconPath: String) {
    DETERMINATION_BAR(R.raw.determination_bar, "determination_bar"),
    PHYSICAL_FITNESS_BAR(R.raw.physical_fitness_bar, "physical_fitness_bar"),
    INTELLIGENCE_BAR(R.raw.intelligence_bar, "intelligence_bar"),
    KNOWLEDGE_BAR(R.raw.knowledge_bar, "knowledge_bar");

    companion object {
        fun fromIconPath(name: String): StatIcon? {
            return entries.find { it.iconPath == name }
        }

        fun fromRawResId(rawResId: Int): StatIcon? {
            return entries.find { it.rawResId == rawResId }
        }
    }
}