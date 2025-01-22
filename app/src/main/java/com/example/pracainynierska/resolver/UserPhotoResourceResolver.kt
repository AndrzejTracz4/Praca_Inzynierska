package com.example.pracainynierska.resolver

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log

class UserPhotoResourceResolver : UserPhotoResourceResolverInterface {

    @SuppressLint("DiscouragedApi")
    override fun getResId(appContext: Context, file: String): Int {
        val resId = appContext.resources.getIdentifier(file, "raw", appContext.packageName)
        Log.d("UserPhotoResourceResolver", "getResId: $resId")
        return resId
    }

}