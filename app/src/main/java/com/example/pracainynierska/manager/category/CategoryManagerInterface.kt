package com.example.pracainynierska.manager.category

interface CategoryManagerInterface {
    suspend fun add(category: String, statisticIds: ArrayList<String>)
    suspend fun edit(id: Int, category: String, statisticIds: ArrayList<String>)
}
