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
    fun editCategory(id: Int, category: String, statisticIds: ArrayList<String>, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("EditCategoryViewModel", "Editing category")

                val updatedCategory = categoryManager.edit(id, category, statisticIds)

                Log.d("EditCategoryViewModel", "Category updated: $updatedCategory")

                if (updatedCategory != null) {
                    playerContext.editPlayerCategory(updatedCategory)
                    onSuccess()
                }
            } catch (e: RequestValidationException) {
                Log.e("EditCategoryViewModel", "EditingError - validation exception")
                onError()
            } catch (e: Exception) {
                Log.e("EditCategoryViewModel - Editing failed", e.message.toString())
                onError()
            }
        }
    }

    fun deleteCategory(id: Int, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("EditCategoryViewModel", "Deleting category")

                val deletedCategory = categoryManager.delete(id)

                Log.d("EditCategoryViewModel", "Category deleted: $deletedCategory")

                if (deletedCategory) {
                    playerContext.removePlayerCategory(id)
                    onSuccess()
                }
            } catch (e: RequestValidationException) {
                Log.e("EditCategoryViewModel", "DeletingError - validation exception")
                onError()
            } catch (e: Exception) {
                Log.e("EditCategoryViewModel - Deleting failed", e.message.toString())
                onError()
            }
        }
    }
}