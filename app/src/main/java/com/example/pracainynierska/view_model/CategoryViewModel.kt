package com.example.pracainynierska.view_model

import android.content.Context
import com.example.pracainynierska.API.model.Category
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.resolver.PhotoResourceResolver

class CategoryViewModel (
    pc: PlayerContextInterface,
    private val appContext: Context
) : AbstractViewModel(pc) {

    private val photoResourceResolver = PhotoResourceResolver()

    fun getPhotoResId(file: String): Int {
        return photoResourceResolver.getResId(appContext, file)
    }

    fun getCategoryById(categoryId: String?): Category? {
        if (categoryId.isNullOrEmpty()) return null
        return playerContext.getPlayerCategories().firstOrNull { it.id.toString() == categoryId }
    }

}