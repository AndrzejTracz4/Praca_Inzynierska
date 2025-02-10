package com.example.pracainynierska.dictionary

enum class AchievementStatus(val key: String, val displayName: String) {
    CLAIMABLE("claimable", "Do odebrania"),
    INCOMPLETE("incomplete", "Nieukończone"),
    CLAIMED("claimed", "Odebrane")
}
