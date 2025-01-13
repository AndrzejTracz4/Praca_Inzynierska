package com.example.pracainynierska.view_model

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import com.example.pracainynierska.API.model.Augment
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.augment.AugmentManager
import com.example.pracainynierska.resolver.UserPhotoResourceResolver

class HomepageViewModel(
    pc: PlayerContextInterface,
    private val appContext: Context,
    private val augmentManager: AugmentManager,
) : AbstractViewModel(pc) {
    private val userPhotoResourceResolver = UserPhotoResourceResolver()

    var userPhotoResId = mutableStateOf(0)

    fun getPhotoResId(): Int {
        val userPhotoPath = playerContext.getPlayer()?.userPhotoPath
        userPhotoResId.value = userPhotoPath
            ?.takeIf { it.isNotBlank() }
            ?.let { userPhotoResourceResolver.getResId(appContext, it) } ?: 0
        return userPhotoResId.value
    }

    fun getAugmentsList(): LiveData<List<Augment>> = augmentManager.getAugmentsList()
}
