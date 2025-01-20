package com.example.pracainynierska.view_model

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.dictionary.UserPhotoDictionary
import com.example.pracainynierska.resolver.UserPhotoResourceResolver

class ProfileViewModel (
    pc: PlayerContextInterface,
    val appContext: Context
) : AbstractViewModel(pc) {

    private val userPhotoResourceResolver = UserPhotoResourceResolver()

    private var selectedAnimationIndex = 1

    var userPhotoResId = mutableStateOf(0)

    fun getPhotoResId(): Int {
        val userPhotoPath = playerContext.getPlayer()?.userPhotoPath
        userPhotoResId.value = if (!userPhotoPath.isNullOrBlank()) {
            userPhotoResourceResolver.getResId(appContext, userPhotoPath)
        } else {
            0
        }
        return userPhotoResId.value
    }

    fun changeUserPhoto() {
        selectedAnimationIndex = (selectedAnimationIndex % 7) + 1
        val newPhotoPath = UserPhotoDictionary.getFileName(selectedAnimationIndex) ?: "user_photo_1"
        playerContext.getPlayer()?.userPhotoPath = newPhotoPath
        userPhotoResId.value = userPhotoResourceResolver.getResId(appContext, newPhotoPath)
    }

}