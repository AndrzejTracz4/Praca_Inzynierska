package com.example.pracainynierska.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.API.api_client.CategoryApi
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.category.CategoryManager

class EditCategoryViewModelFactory(
    private val playerContext: PlayerContextInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditCategoryViewModel::class.java)) {
            val api = CategoryApi(playerContext)
            val categoryManager = CategoryManager(api)
            return EditCategoryViewModel(playerContext, categoryManager) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}