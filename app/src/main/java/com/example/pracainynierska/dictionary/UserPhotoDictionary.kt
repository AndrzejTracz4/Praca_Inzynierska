package com.example.pracainynierska.dictionary

object UserPhotoDictionary {

    private val lottieFiles: Map<Int, String> = mapOf(
        1 to "user_photo_1",
        2 to "user_photo_2",
        3 to "user_photo_3",
        4 to "user_photo_4",
        5 to "user_photo_5",
        6 to "user_photo_6",
        7 to "user_photo_7",
        8 to "user_photo_8"
    )

    fun getFileName(key: Int): String? {
        return lottieFiles[key]
    }

    fun getKey(fileName: String): Int? {
        return lottieFiles.entries.find { it.value == fileName }?.key
    }

}
