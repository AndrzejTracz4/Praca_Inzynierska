package com.example.pracainynierska.view

import BottomMenu
import GradientLevelProgressBar
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import kotlinx.coroutines.launch

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

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope();
    val selectedItem = remember { mutableStateOf(0) }

    val items = listOf(
        DrawerItem("Strona Główna", Icons.Default.Home, "HomepageView"),
        DrawerItem("Profil", Icons.Default.AccountBox, "ProfileView"),
        DrawerItem("Statystyki", Icons.Default.AddCircle, "StatisticsView"),
        DrawerItem("Kalendarz", Icons.Default.Face, "CalendarView"),
        DrawerItem("Sklep", Icons.Default.MailOutline, "ShopView"),
        DrawerItem("Ustawienia", Icons.Default.Settings, "SettingsView"),
        DrawerItem("Wyloguj", Icons.Default.Lock, "LogoutView")
    )


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF4C0949),
                                Color(0xFF470B93)
                            ),
                            start = Offset(0f, Float.POSITIVE_INFINITY),
                            end = Offset(0f, 0f)
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 48.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp), // Dodaj margines na dole
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Ikona aplikacji
                    Image(
                        painter = painterResource(id = R.drawable.questa_logo), // Zmień 'your_image' na nazwę swojego pliku
                        contentDescription = "App Icon",
                        modifier = Modifier.size(40.dp) // Rozmiar ikony
                    )

                    Spacer(modifier = Modifier.width(8.dp)) // Odstęp między ikoną a nazwą

                    // Nazwa aplikacji
                    Text(
                        text = "Questa", // Wprowadź nazwę aplikacji
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Wersja: 1.0.0",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                items.take(items.size - 2).forEachIndexed { index, item ->
                    CustomDrawerItem(
                        text = item.text,
                        isSelected = selectedItem.value == index,
                        onClick = {
                            selectedItem.value = index
                            navController.navigate(item.route) // Użyj trasy z klasy DrawerItem
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.text,
                                modifier = Modifier.size(20.dp), // Ustawienie rozmiaru ikony
                                tint = Color.White // Ustawienie koloru ikony
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                items.takeLast(2).forEachIndexed { index, item ->
                    CustomDrawerItem(
                        text = item.text,
                        isSelected = selectedItem.value == (items.size - 2 + index),
                        onClick = {
                            selectedItem.value = items.size - 2 + index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.text,
                                modifier = Modifier.size(20.dp), // Ustawienie rozmiaru ikony
                                tint = Color.White // Ustawienie koloru ikony
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    ) {
        // Scaffold jako zawartość wewnątrz ModalNavigationDrawer
        Scaffold(
            topBar = {
                TopMenu(
                    loginViewModel = loginViewModel,
                    drawerState = drawerState,
                    onDrawerOpen = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            bottomBar = {
                BottomMenu(navController = navController)
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF4C0949),
                                Color(0xFF470B93)
                            ),
                            start = Offset(0f, Float.POSITIVE_INFINITY),
                            end = Offset(0f, 0f)
                        )
                    )
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Spacer(modifier = Modifier.height(0.dp))

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
}