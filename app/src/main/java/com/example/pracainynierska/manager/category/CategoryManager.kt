package com.example.pracainynierska.manager.category

import com.example.pracainynierska.API.api_client.CategoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryManager(private val apiClient: CategoryApi) : CategoryManagerInterface {
    override suspend fun add(category: String, statisticIds: ArrayList<String>) {
        return withContext(Dispatchers.IO) {
            apiClient.addCategory(category, statisticIds)
        }
    }
}