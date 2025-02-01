package com.example.pracainynierska.manager.category

import com.example.pracainynierska.API.api_client.CategoryApi
import com.example.pracainynierska.API.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryManager(private val apiClient: CategoryApi) : CategoryManagerInterface {
    override suspend fun add(category: String, statisticIds: ArrayList<String>): Category? {
        return withContext(Dispatchers.IO){
            apiClient.addCategory(category, statisticIds)
        }
    }

    override suspend fun edit(id: Int, category: String, statisticIds: ArrayList<String>): Category? {
        return withContext(Dispatchers.IO) {
            apiClient.editCategory(id, category, statisticIds)
        }
    }

    override suspend fun delete(id: Int): Boolean {
        return withContext(Dispatchers.IO){
            apiClient.deleteCategory(id)
        }
    }
}