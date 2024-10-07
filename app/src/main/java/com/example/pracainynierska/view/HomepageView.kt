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
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomepageView(navController: NavController, userRepository: UserRepository, username: String?) {

    val focusManager = LocalFocusManager.current
    var initialUserPhotoPath = ""
    var userLevel = 1

    // Pobranie instancji LoginViewModel przy użyciu LoginViewModelFactory
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(userRepository)
    )


    loginViewModel.user.observeAsState().value.let {
        if (username != null) {
            loginViewModel.fetchUser(username)
        }
        if (it != null) {
            initialUserPhotoPath = it.userPhotoPath.toString()
        }
        if (it != null) {
            userLevel = it.level
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

    // Obserwowanie danych z LiveData jako Compose State
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

                        // Miejsce na zdjęcie użytkownika
                        UserImagePicker(userRepository, initialUserPhotoPath = initialUserPhotoPath, username)

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

                            GradientProgressBar(progress = 100f) // Procent doświadczenia

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

                                Spacer(modifier = Modifier.width(115.dp))

                                Text(
                                    text = "0/100", // Doświadczenie
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
}