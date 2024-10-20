package com.example.pracainynierska.view

import BottomMenu
import GradientLevelProgressBar
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.pracainynierska.model.User
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomepageView(navController: NavController, loginViewModel: LoginViewModel) {

    val focusManager = LocalFocusManager.current
    var userLevel = 1
    var userExperience = 0f

    loginViewModel.user.observeAsState().value.let {
        if (it != null) {
            userLevel = it.level
            userExperience = it.experience
        }
    }

    val levelNames = mapOf(
        1 to "Rekrut",
        2 to "Szeregowy",
        3 to "Starszy Szeregowy",
        4 to "Kapral",
        5 to "Starszy Kapral",
        6 to "Plutonowy",
        7 to "Sierżant",
        8 to "Starszy Sierżant",
        9 to "Major Sierżant",
        10 to "Chorąży",
        11 to "Starszy Chorąży",
        12 to "Sztabowy Chorąży",
        13 to "Podporucznik",
        14 to "Porucznik",
        15 to "Starszy Porucznik",
        16 to "Kapitan",
        17 to "Sztabowy Kapitan",
        18 to "Major",
        19 to "Starszy Major",
        20 to "Podpułkownik",
        21 to "Pułkownik",
        22 to "Starszy Pułkownik",
        23 to "Generał Brygady",
        24 to "Generał Dywizji",
        25 to "Generał Broni",
        26 to "Generał Armii",
        27 to "Marszałek",
        28 to "Marszałek Polowy",
        29 to "Naczelny Wódz",
        30 to "Mistrz Strategii"
    )

    val userRank = levelNames[userLevel] ?: "Nieznany poziom"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF4C0949), // Bottom color
                        Color(0xFF470B93)  // Top color
                    ),
                    start = Offset(0f, Float.POSITIVE_INFINITY), // Start at the bottom
                    end = Offset(0f, 0f)  // End at the top
                )
            )
    ) {
        Scaffold(
            topBar = {
                TopMenu(loginViewModel)
            },

            containerColor = Color.Transparent,

            bottomBar = {
                BottomMenu(navController = navController)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Spacer(modifier = Modifier.height(70.dp))

                // użytkownik
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp) // 2x wysokość TopBar
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0x19FFFFFF))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Miejsce na zdjęcie użytkownika
                        UserImagePicker(loginViewModel)

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = userRank, // Ranga użytkownika
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
                                text = "Poziom $userLevel", // Poziom użytkownika
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

                            GradientLevelProgressBar(loginViewModel) // Procent doświadczenia

                            Spacer(modifier = Modifier.height(4.dp))

                            Row {
                                Text(
                                    text = "Doświadczenie",
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

                                Spacer(modifier = Modifier.width(105.dp))

                                Text(
                                    text = "${userExperience.toInt()}/100", // Doświadczenie
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

                Spacer(modifier = Modifier.height(8.dp))

                // statystyki
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0x19FFFFFF))
                        .padding(4.dp)
                ){
                    GradientStatsProgressBars(loginViewModel)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Aktywne boostery
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp) // 2x wysokość TopBar
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0x19FFFFFF))
                        .padding(horizontal = 10.dp, vertical = 1.dp)
                ) {
                    Column (
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Aktywne boostery",
                            fontSize = 13.sp,
                            color = Color.White,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(3f, 1f),
                                    blurRadius = 3f
                                )
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 5.dp)
                        )

                        Spacer(modifier = Modifier.height(1.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp) // 2x wysokość TopBar
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0x14FFFFFF))
                                .padding(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Zadanie dnia
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp) // 2x wysokość TopBar
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0x19FFFFFF))
                        .padding(horizontal = 10.dp, vertical = 1.dp)
                ) {
                    Column (
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Wyzwanie dnia!",
                            fontSize = 13.sp,
                            color = Color.White,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(3f, 1f),
                                    blurRadius = 3f
                                )
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 5.dp)
                        )

                        Spacer(modifier = Modifier.height(1.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp) // 2x wysokość TopBar
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0x14FFFFFF))
                                .padding(16.dp)
                        )
                    }
                }

            }
        }

    }
}