package com.example.pracainynierska.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.Exception.RequestValidationException
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.category.CategoryManagerInterface
import kotlinx.coroutines.launch

class EditCategoryViewModel (
    pc: PlayerContextInterface,
    private val categoryManager: CategoryManagerInterface
) : AbstractViewModel(pc) {
    fun editCategory(id: Int, category: String, statisticIds: ArrayList<String>) {
        viewModelScope.launch {
            try {
                Log.d("EditCategoryViewModel", "Editing category")
                categoryManager.edit(id, category, statisticIds)
            } catch (e: RequestValidationException) {
                Log.e("EditCategoryViewModel", "EditingError - validation exception")
                throw e
            } catch (e: Exception) {
                Log.e("EditCategoryViewModel - Editing failed", e.message.toString())
                throw e
            }
        }
    }
}