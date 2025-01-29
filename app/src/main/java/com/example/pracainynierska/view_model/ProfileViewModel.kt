package com.example.pracainynierska.view_model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.api_client.PlayerApi
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.dictionary.UserPhotoDictionary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.pracainynierska.resolver.PhotoResourceResolver

class ProfileViewModel (
    pc: PlayerContextInterface,
    private val playerApi: PlayerApi,
    private val appContext: Context
) : AbstractViewModel(pc) {

    private val photoResourceResolver = PhotoResourceResolver()

    private var selectedAnimationIndex = 1

    var userPhotoResId = mutableStateOf(0)

    var newPhotoPath by mutableStateOf<String?>(null)
        private set

    fun getPhotoResId(): Int {
        val userPhotoPath = playerContext.getPlayer()?.userPhotoPath
        userPhotoResId.value = if (!userPhotoPath.isNullOrBlank()) {
            photoResourceResolver.getResId(appContext, userPhotoPath)
        } else {
            0
        }
        return userPhotoResId.value
    }

    fun changeUserPhoto() {
        selectedAnimationIndex = (selectedAnimationIndex % 7) + 1
        newPhotoPath = UserPhotoDictionary.getFileName(selectedAnimationIndex) ?: "user_photo_1"
        playerContext.getPlayer()?.userPhotoPath = newPhotoPath as String
        userPhotoResId.value = photoResourceResolver.getResId(appContext, newPhotoPath!!)
    }

    fun uploadImageToApi() {
        viewModelScope.launch {
            if (newPhotoPath != null) {
                try {
                    Log.d("ProfileViewModel", "Uploading new image: $newPhotoPath")
                    withContext(Dispatchers.IO) {
                        playerApi.updateUserPhotoPath(newPhotoPath!!)
                    }
                    newPhotoPath = null
                } catch (e: Exception) {
                    Log.e("ProfileViewModel", "Failed to upload image: ${e.message}")
                }
            } else {
                Log.d("ProfileViewModel", "No changes in user photo, skipping API call.")
            }
        }
    }

}