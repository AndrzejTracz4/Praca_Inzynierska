package com.example.pracainynierska.resolver

import android.content.Context

interface PhotoResourceResolverInterface {

    fun getResId(appContext: Context, file: String): Int

}