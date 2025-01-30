package com.example.pracainynierska.dictionary

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

enum class StatGradient(val gradient: Brush) {
    GRADIENT_1(Brush.horizontalGradient(colors = listOf(Color(0xFFFFA726), Color(0xFFFF7043)))),
    GRADIENT_2(Brush.horizontalGradient(colors = listOf(Color(0xFF66BB6A), Color(0xFF43A047)))),
    GRADIENT_3(Brush.horizontalGradient(colors = listOf(Color(0xFFAB47BC), Color(0xFF8E24AA)))),
    GRADIENT_4(Brush.horizontalGradient(colors = listOf(Color(0xFF29B6F6), Color(0xFF0288D1))));

    companion object {
        fun getGradientByIndex(index: Int): StatGradient {
            return entries[index % entries.size]
        }
    }
}