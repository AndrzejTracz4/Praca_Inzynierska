package com.example.pracainynierska.ui_view_components.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.example.pracainynierska.view_model.LoginViewModel
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.example.pracainynierska.R

@Composable
fun UserImagePicker(loginViewModel: LoginViewModel) {

    // Zmienna do przechowywania wybranej animacji
    var selectedAnimationIndex by remember { mutableIntStateOf(0) }

    // Lista plików Lottie jako stałe ścieżki
    val lottieFiles = listOf(
        "app/src/main/res/raw/user_photo_1.json",
        "app/src/main/res/raw/user_photo_2.json",
        "app/src/main/res/raw/user_photo_3.json",
        "app/src/main/res/raw/user_photo_5.json",
        "app/src/main/res/raw/user_photo_6.json",
        "app/src/main/res/raw/user_photo_7.json",
        "app/src/main/res/raw/user_photo_8.json"
    )

    // Pobranie animacji Lottie na podstawie ścieżki
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

    //Obserwer do LiveData
    loginViewModel.user.observeAsState().value.let {
//        if (userUUID != null) {
//            loginViewModel.fetchUser(userUUID)
//            Log.d("UserImagePicker", "fetcheduser: $it")
//        }
        if (it != null) {
            val userPhotoPath = it.userPhotoPath
            selectedAnimationIndex = lottieFiles.indexOf(userPhotoPath).takeIf { it >= 0 } ?: 0
        }
    }

    var isAnimationPlaying by remember { mutableStateOf(false) }

    // Box dla zdjęcia użytkownika
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(50.dp))
            .clickable {
                // Zmiana animacji po kliknięciu
                selectedAnimationIndex = (selectedAnimationIndex + 1) % lottieFiles.size

                // Przypisz nową ścieżkę na podstawie indeksu
                val newPhotoPath = lottieFiles[selectedAnimationIndex]

                // Zmiana stanu odtwarzania animacji
                isAnimationPlaying = true

                // Zaktualizowanie ścieżki w bazie danych za pomocą ViewModel
                loginViewModel.updateUserPhotoPath(newPhotoPath)

            }
    ) {
        // Wyświetlanie wybranej animacji Lottie
        LottieAnimation(
            composition = composition,
            iterations = if (isAnimationPlaying) LottieConstants.IterateForever else 1 // Odtwarzaj w nieskończoność, jeśli stan jest włączony
        )
    }
}
