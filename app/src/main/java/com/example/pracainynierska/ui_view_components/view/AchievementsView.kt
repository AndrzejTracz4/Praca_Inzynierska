package com.example.pracainynierska.ui_view_components.view

import BottomMenu
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.ui.components.ModalDrawer
import com.example.pracainynierska.ui_view_components.components.AchievementCard
import com.example.pracainynierska.ui_view_components.components.TopMenu
import com.example.pracainynierska.view_model.LoginViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AchievementsView(navController: NavController, loginViewModel: LoginViewModel) {

    val focusManager = LocalFocusManager.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    ModalDrawer(navController = navController, drawerState = drawerState) {
        Scaffold(
            topBar = {
                TopMenu(
                    navController = navController,
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
        ) {
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
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = { focusManager.clearFocus() })
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(start = 16.dp, end = 16.dp, top = 84.dp, bottom = 84.dp), // Dodanie odstępu od końca ekranu
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Osiągnięcia",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    AchievementCard(
                        title = "Przebudzenie mocy I",
                        description = "Wykonaj 2 zadania",
                        exp = 50,
                        coins = 10,
                        tasksCompleted = 2,
                        tasksToDo = 2,
                        onClaimClick = {
                            // Obsługa kliknięcia todo
                        }
                    )

                    AchievementCard(
                        title = "Przebudzenie mocy II",
                        description = "Wykonaj 5 zadań",
                        exp = 100,
                        coins = 20,
                        tasksCompleted = 5,
                        tasksToDo = 5,
                        onClaimClick = {
                            // Obsługa kliknięcia todo
                        }
                    )

                    AchievementCard(
                        title = "Przebudzenie mocy III",
                        description = "Wykonaj 10 zadań",
                        exp = 200,
                        coins = 30,
                        tasksCompleted = 5,
                        tasksToDo = 10,
                        onClaimClick = {
                            // Obsługa kliknięcia todo
                        }
                    )

                    AchievementCard(
                        title = "Przebudzenie mocy IV",
                        description = "Wykonaj 50 zadań",
                        exp = 500,
                        coins = 100,
                        tasksCompleted = 5,
                        tasksToDo = 50,
                        onClaimClick = {
                            // Obsługa kliknięcia todo
                        }
                    )
                }
            }
        }
    }
}
