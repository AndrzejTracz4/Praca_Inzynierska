package com.example.pracainynierska.view_model

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import com.example.pracainynierska.API.model.Augment
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.augment.AugmentManager
import com.example.pracainynierska.resolver.UserPhotoResourceResolver

class HomepageViewModel(
    pc: PlayerContextInterface,
    val appContext: Context
) : AbstractViewModel(pc) {

    private val userPhotoResourceResolver = UserPhotoResourceResolver()

    var userPhotoResId = mutableIntStateOf(0)

    fun getPhotoResId(): Int {
        val userPhotoPath = playerContext.getPlayer()?.userPhotoPath
        userPhotoResId.intValue = userPhotoPath?.takeIf { it.isNotBlank() }
            ?.let { userPhotoResourceResolver.getResId(appContext, it) } ?: 0
        return userPhotoResId.intValue
    }
}
