package com.example.pracainynierska.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.Exception.RequestValidationException
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.category.CategoryManagerInterface
import kotlinx.coroutines.launch

class AddCategoryViewModel (
    pc: PlayerContextInterface,
    private val categoryManager: CategoryManagerInterface
) : AbstractViewModel(pc) {
    fun addCategory(category: String, statisticIds: ArrayList<String>, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("AddCategoryViewModel", "Adding category")
                val newCategory = categoryManager.add(category, statisticIds)

                Log.d("AddCategoryViewModel", "Category added: $newCategory")

                if (newCategory != null) {
                    playerContext.addPlayerCategory(newCategory)
                    onSuccess()
                }
            } catch (e: RequestValidationException) {
                Log.e("AddCategoryViewModel", "CreationError - validation exception")
                onError()
            } catch (e: Exception) {
                Log.e("AddCategoryViewModel - Creation failed", e.message.toString())
                onError()
            }
        }
    }
}