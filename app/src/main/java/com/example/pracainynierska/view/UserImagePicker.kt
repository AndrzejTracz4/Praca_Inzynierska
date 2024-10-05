package com.example.pracainynierska.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.example.pracainynierska.repository.UserRepository
import com.example.pracainynierska.viewmodel.LoginViewModel
import com.example.pracainynierska.viewmodel.LoginViewModelFactory
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.example.pracainynierska.R
import kotlinx.coroutines.delay

@Composable
fun UserImagePicker(userRepository: UserRepository, initialUserPhotoPath: String?, username: String?) {

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(userRepository)
    )

    // Zmienna do przechowywania wybranej animacji
    var selectedAnimationIndex by remember { mutableIntStateOf(0) }

    // Lista plików Lottie jako stałe ścieżki
    val lottieFiles = listOf(
        "app/src/main/res/raw/user_photo_1.json",
        "app/src/main/res/raw/user_photo_2.json"
    )

    // Pobranie animacji Lottie na podstawie ścieżki
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            when (selectedAnimationIndex) {
                0 -> R.raw.user_photo_1
                1 -> R.raw.user_photo_2
                else -> R.raw.user_photo_1 // Default in case of out of bounds
            }
        )
    )

    //Obserwer do LiveData
    loginViewModel.user.observeAsState().value.let {
        if (username != null) {
            loginViewModel.fetchUser(username)
            Log.d("UserImagePicker", "fetcheduser: $it")
        }
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
            .background(Color.Gray, shape = RoundedCornerShape(50.dp))
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
