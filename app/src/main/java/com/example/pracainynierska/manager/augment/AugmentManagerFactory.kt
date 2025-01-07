package com.example.pracainynierska.manager.augment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.context.PlayerContextInterface

class AugmentManagerFactory(
    private val playerContext: PlayerContextInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AugmentManager::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AugmentManager(playerContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
