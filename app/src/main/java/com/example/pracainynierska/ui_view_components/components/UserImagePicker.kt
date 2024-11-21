package com.example.pracainynierska.ui_view_components.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pracainynierska.R

@Composable
fun UserImagePicker(
    selectedAnimationIndex: Int,
    onImageChanged: (String) -> Unit?
){

    val lottieFiles = listOf(
        "app/src/main/res/raw/user_photo_1.json",
        "app/src/main/res/raw/user_photo_2.json",
        "app/src/main/res/raw/user_photo_3.json",
        "app/src/main/res/raw/user_photo_4.json",
        "app/src/main/res/raw/user_photo_5.json",
        "app/src/main/res/raw/user_photo_6.json",
        "app/src/main/res/raw/user_photo_7.json",
        "app/src/main/res/raw/user_photo_8.json"
    )

    // Pobranie animacji Lottie na podstawie wybranego indeksu
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            when (selectedAnimationIndex) {
                0 -> R.raw.user_photo_1
                1 -> R.raw.user_photo_2
                2 -> R.raw.user_photo_3
                3 -> R.raw.user_photo_5
                4 -> R.raw.user_photo_6
                5 -> R.raw.user_photo_7
                6 -> R.raw.user_photo_8
                else -> R.raw.user_photo_1
            }
        )
    )

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(50.dp))
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }

    val newPhotoPath = lottieFiles[selectedAnimationIndex]
    onImageChanged(newPhotoPath)
}
