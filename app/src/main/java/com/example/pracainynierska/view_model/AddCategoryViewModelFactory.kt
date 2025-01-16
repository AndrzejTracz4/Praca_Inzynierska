package com.example.pracainynierska.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pracainynierska.API.api_client.CategoryApi
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.category.CategoryManager

class AddCategoryViewModelFactory(
    private val playerContext: PlayerContextInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCategoryViewModel::class.java)) {
            val api = CategoryApi(playerContext)
            val categoryManager = CategoryManager(api)
            return AddCategoryViewModel(playerContext, categoryManager) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
