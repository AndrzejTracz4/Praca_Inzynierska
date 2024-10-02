package com.example.pracainynierska.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.example.pracainynierska.repository.UserRepository
import com.example.pracainynierska.viewmodel.LoginViewModel
import com.example.pracainynierska.viewmodel.LoginViewModelFactory
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.example.pracainynierska.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomepageView(navController: NavController, userRepository: UserRepository, username: String?) {

    val focusManager = LocalFocusManager.current

    // Pobranie instancji LoginViewModel przy użyciu LoginViewModelFactory
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(userRepository)
    )

    // Obserwowanie danych z LiveData jako Compose State
    val loggedInUser by loginViewModel.loggedInUser.observeAsState()

    val initialUserPhotoPath = loggedInUser?.userPhotoPath

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Scaffold(
            topBar = {

                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0x4DFFFFFF)
                    ),

                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp)),

                    title = {
                        Text(
                            text = username ?: "Loading...",
                            fontSize = 30.sp,
                            color = Color.White,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(3f, 1f),
                                    blurRadius = 3f
                                )
                            )
                        )
                    },

                    navigationIcon = {
                        IconButton(onClick = {
                            // Akcja, np. wylogowanie todo
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                                )
                        }
                    }
                )
            },
            containerColor = Color(0xFF5b6d9d)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(70.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp) // 2x wysokość TopBar
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0x33FFFFFF))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        //val loggedInUser = loginViewModel.loggedInUser.value
                        Log.d("Homepage", "Logged in user: $loggedInUser")
                        // Miejsce na zdjęcie użytkownika
                        UserImagePicker(userRepository, initialUserPhotoPath = initialUserPhotoPath)

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = "Szeregowy", // Ranga użytkownika
                                fontSize = 20.sp,
                                color = Color.White,
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color.Black,
                                        offset = Offset(3f, 1f),
                                        blurRadius = 3f
                                    )
                                )
                            )

                            Text(
                                text = "Poziom 5", // Poziom użytkownika
                                fontSize = 16.sp,
                                color = Color.White,
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color.Black,
                                        offset = Offset(3f, 1f),
                                        blurRadius = 3f
                                    )
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            GradientProgressBar(progress = 1f) // Procent doświadczenia

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "0/10", // Doświadczenie
                                fontSize = 12.sp,
                                color = Color.White,
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color.Black,
                                        offset = Offset(3f, 1f),
                                        blurRadius = 3f
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GradientProgressBar(progress: Float) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color.Green, Color.Yellow, Color.Red),
        startX = 0.0f,
        endX = 600.0f * progress
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(8.dp)
                .background(gradient)
        )
    }
}

@Composable
fun UserImagePicker(userRepository: UserRepository, initialUserPhotoPath: String?) {

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(userRepository)
    )

    val loggedInUser by loginViewModel.loggedInUser.observeAsState()

    // Lista plików Lottie jako stałe ścieżki
    val lottieFiles = listOf(
        "app/src/main/res/raw/user_photo_1.json",
        "app/src/main/res/raw/user_photo_2.json"
    )

    // Zmienna do przechowywania wybranej animacji
    var selectedAnimationIndex by remember { mutableIntStateOf(initialUserPhotoPath?.let { lottieFiles.indexOf(it) } ?: 0) }

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
                Log.d("UserImagePicker", "Selected new photo path: $newPhotoPath")

                // Zaktualizowanie ścieżki w bazie danych za pomocą ViewModel
                //val loggedInUser = loginViewModel.loggedInUser.value
                Log.d("HomepageView", "loggedInUser before calling updateUserPhotoPath: $loggedInUser")

                loginViewModel.updateUserPhotoPath(newPhotoPath)

//                if (loggedInUser != null) {
//                    loginViewModel.updateUserPhotoPath(newPhotoPath)
//                } else {
//                    Log.d("UserImagePicker", "Logged in user is null, cannot update photo.")
//                }
            }
    ) {
        // Wyświetlanie wybranej animacji Lottie
        LottieAnimation(composition = composition)
    }
}
