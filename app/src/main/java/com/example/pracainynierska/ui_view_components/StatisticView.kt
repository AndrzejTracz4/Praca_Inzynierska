package com.example.pracainynierska.ui_view_components

import BottomMenu
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.ui.components.ModalDrawer
import com.example.pracainynierska.ui_view_components.components.GradientStatsProgressBars
import com.example.pracainynierska.ui_view_components.components.TopMenu
import com.example.pracainynierska.view_model.LoginViewModel
import kotlinx.coroutines.launch

data class Statistic(val name: String, val progress: Float)
data class Category(val name: String, val stats: List<Statistic>)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StatisticView(navController: NavController, loginViewModel: LoginViewModel) {
    val focusManager = LocalFocusManager.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val statisticsData = listOf(
        Category(
            "Kategoria 1", listOf(
                Statistic("Determinacja", 80f),
                Statistic("Sprawność fizyczna", 65f),
                Statistic("Inteligencja", 75f),
                Statistic("Wiedza", 90f)
            )
        ),
        Category(
            "Kategoria 2", listOf(
                Statistic("Cierpliwość", 70f),
                Statistic("Kreatywność", 85f),
                Statistic("Zdolności przywódcze", 60f)
            )
        ),
        Category(
            "Kategoria 3", listOf(
                Statistic("Zarządzanie stresem", 50f),
                Statistic("Adaptacja do zmian", 90f)
            )
        ),
        Category(
            "Kategoria 4", listOf(
                Statistic("Komunikacja", 80f),
                Statistic("Praca zespołowa", 85f),
                Statistic("Rozwiązywanie problemów", 70f)
            )
        ),
        Category(
            "Kategoria 5", listOf(
                Statistic("Innowacyjność", 90f),
                Statistic("Elastyczność", 80f),
                Statistic("Zarządzanie czasem", 75f)
            )
        )
    )

    // State for search query
    var searchQuery by remember { mutableStateOf("") }

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
        ) { innerPadding ->
            Column(
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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()) // Włączamy przewijanie
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                // Search bar
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = {
                        Text(
                            text = "Wyszukaj kategorię",
                            color = Color.White
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White.copy(alpha = 0.2f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                )


                // Filtruj kategorie, jeśli zapytanie jest wpisane
                val filteredCategories = if (searchQuery.isNotEmpty()) {
                    statisticsData.filter { category ->
                        category.name.contains(searchQuery, ignoreCase = true)
                    }
                } else {
                    statisticsData
                }

                // Wyświetlanie kategorii i statystyk
                filteredCategories.forEach { category ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .background(Color(0x19FFFFFF), shape = RoundedCornerShape(10.dp))
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 4.dp)
                        ) {
                            // Tytuł kategorii
                            Text(
                                text = category.name,
                                fontSize = 18.sp,
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            // Wyświetlanie statystyk w danej kategorii
                            category.stats.forEachIndexed { index, stat ->
                                val gradient = when (index % 4) {
                                    0 -> Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFFFFA726),
                                            Color(0xFFFF7043)
                                        )
                                    )

                                    1 -> Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFF66BB6A),
                                            Color(0xFF43A047)
                                        )
                                    )

                                    2 -> Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFFAB47BC),
                                            Color(0xFF8E24AA)
                                        )
                                    )

                                    else -> Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(
                                                0xFF29B6F6
                                            ), Color(0xFF0288D1)
                                        )
                                    )
                                }

                                // Box dla każdej statystyki z gradientowym tłem
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Column {
                                        GradientStatsProgressBars(
                                            stats = listOf(stat.name to stat.progress),
                                            gradients = listOf(gradient),
                                            icons = listOf(
                                                R.raw.determination_bar, R.raw.physical_fitness_bar,
                                                R.raw.intelligence_bar, R.raw.knowledge_bar
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}
