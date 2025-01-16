package com.example.pracainynierska.manager.category

interface CategoryManagerInterface {
    suspend fun add(category: String, statisticIds: ArrayList<String>)
}
