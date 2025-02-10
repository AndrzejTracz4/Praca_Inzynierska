package com.example.pracainynierska.dictionary

import androidx.compose.ui.graphics.Color

enum class TaskStatus(val key: String, val displayName: String, val color: Color) {
    NEW("new", "Nowe", Color(0xFFf39c12)),
    ACCEPTED("accepted", "Zaakceptowane", Color(0xFF3498db)),
    COMPLETED("completed", "Ukończone", Color(0xFF2ecc71)),
    DECLINED("declined", "Odrzucone", Color(0xFFe74c3c)),
    EXPIRED("failed", "Wygasło", Color(0xFF95a5a6));

    companion object {
        fun fromKey(key: String): TaskStatus? {
            return entries.find { it.key == key }
        }
    }
}

