package com.example.pracainynierska.manager.category

import com.example.pracainynierska.API.model.Category

interface CategoryManagerInterface {
    suspend fun add(category: String, statisticIds: ArrayList<String>): Category?
    suspend fun edit(id: Int, category: String, statisticIds: ArrayList<String>): Category?
    suspend fun delete(id: Int): Boolean
}
