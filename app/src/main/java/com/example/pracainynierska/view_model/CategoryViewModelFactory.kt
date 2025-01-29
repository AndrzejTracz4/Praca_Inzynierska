package com.example.pracainynierska.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.context.PlayerContextInterface

class CategoryViewModelFactory(
    private val playerContext: PlayerContextInterface,
    private val appContext: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(playerContext, appContext) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
