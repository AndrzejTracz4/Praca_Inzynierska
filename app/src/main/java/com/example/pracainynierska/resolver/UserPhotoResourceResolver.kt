package com.example.pracainynierska.resolver

import android.annotation.SuppressLint
import android.content.Context

class UserPhotoResourceResolver : UserPhotoResourceResolverInterface {

    @SuppressLint("DiscouragedApi")
    override fun getResId(appContext: Context, file: String): Int {
        return appContext.resources.getIdentifier(file, "raw", appContext.packageName)
    }

}