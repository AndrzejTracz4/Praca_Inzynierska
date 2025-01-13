package com.example.pracainynierska.resolver

import android.content.Context

interface UserPhotoResourceResolverInterface {

    fun getResId(appContext: Context, file: String): Int

}