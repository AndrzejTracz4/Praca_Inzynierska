package com.example.pracainynierska.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pracainynierska.R
import com.example.pracainynierska.ui_view_components.components.CustomDrawerItem
import com.example.pracainynierska.ui_view_components.view.DrawerItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDrawer(
    navController: NavController,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {

    val scope = rememberCoroutineScope()

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val items = listOf(
        DrawerItem("Strona Główna", R.drawable.home, "HomepageView"),
        DrawerItem("Profil", R.drawable.profile, "HomepageView"),
        DrawerItem("Statystyki", R.drawable.stats, "StatisticView"),
        DrawerItem("Kalendarz", R.drawable.calendar, "CalendarsView"),
        DrawerItem("Sklep", R.drawable.shop, "ShopView"),
        DrawerItem("Ustawienia", R.drawable.settings, "HomepageView"),
        DrawerItem("Wyloguj", R.drawable.logout, "LoginView")
    )

    // Zmienna do śledzenia zaznaczonego elementu
    val selectedItemIndex = remember { mutableStateOf(0) }

    // Użycie LaunchedEffect do aktualizacji indeksu zaznaczonego elementu
    LaunchedEffect(currentRoute) {
        selectedItemIndex.value = items.indexOfFirst { it.route == currentRoute }.takeIf { it != -1 } ?: 0
    }

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
                // Drawer header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.questa_logo),
                        contentDescription = "App Icon",
                        modifier = Modifier.size(40.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Questa",
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

                // Main menu items
                items.take(items.size - 2).forEachIndexed { index, item ->
                    CustomDrawerItem(
                        text = item.text,
                        isSelected = selectedItemIndex.value == index,
                        onClick = {
                            if (currentRoute != item.route) { // Sprawdzanie, czy nie jesteśmy już w tym widoku
                                selectedItemIndex.value = index
                                navController.navigate(item.route) // Nawiguj do nowego widoku
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        },
                        icon = {
                            Image(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = item.text,
                                modifier = Modifier.size(20.dp),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                // Footer items (last 2)
                items.takeLast(2).forEachIndexed { index, item ->
                    CustomDrawerItem(
                        text = item.text,
                        isSelected = selectedItemIndex.value == (items.size - 2 + index),
                        onClick = {
                            selectedItemIndex.value = items.size - 2 + index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Image(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = item.text,
                                modifier = Modifier.size(20.dp),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    ) {
        content()
    }
}
