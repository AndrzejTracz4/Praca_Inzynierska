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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pracainynierska.R
import com.example.pracainynierska.ui_view_components.components.CustomDrawerItem
import com.example.pracainynierska.ui_view_components.components.DrawerItem
import com.example.pracainynierska.dictionary.ViewRoutes
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
        DrawerItem(stringResource(R.string.homepage), R.drawable.home, ViewRoutes.HOMEPAGE),
        DrawerItem(stringResource(R.string.profile), R.drawable.profile, ViewRoutes.PROFILE),
        DrawerItem(stringResource(R.string.categories), R.drawable.stats, ViewRoutes.CATEGORIES),
        DrawerItem(stringResource(R.string.achievements), R.drawable.achievements, ViewRoutes.ACHIEVEMENTS),
        DrawerItem(stringResource(R.string.calendar), R.drawable.calendar, ViewRoutes.CALENDAR),
        DrawerItem(stringResource(R.string.shop), R.drawable.shop, ViewRoutes.SHOP),
        DrawerItem(stringResource(R.string.logout), R.drawable.logout, ViewRoutes.LOGOUT)
    )

    val selectedItemIndex = remember { mutableIntStateOf(0) }

    // Using LaunchedEffect to update the index of a selected item
    LaunchedEffect(currentRoute) {
        selectedItemIndex.intValue = items.indexOfFirst { it.route.viewName == currentRoute }.takeIf { it != -1 } ?: -1
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.questa_logo),
                        contentDescription = stringResource(R.string.icon_logo_questa_description),
                        modifier = Modifier.size(40.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = stringResource(R.string.app_name),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = stringResource(R.string.version, "1.0.0"),
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Main menu items
                items.take(items.size - 1).forEachIndexed { index, item ->
                    CustomDrawerItem(
                        text = item.title,
                        isSelected = selectedItemIndex.intValue == index,
                        onClick = {
                            if (currentRoute != item.route.viewName) {
                                selectedItemIndex.intValue = index
                                navController.navigate(item.route.viewName)
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        },
                        icon = {
                            Image(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = item.title,
                                modifier = Modifier.size(20.dp),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                // Footer item
                items.takeLast(1).forEachIndexed { index, item ->
                    CustomDrawerItem(
                        text = item.title,
                        isSelected = selectedItemIndex.intValue == (items.size - 2 + index),
                        onClick = {
                            selectedItemIndex.intValue = items.size - 2 + index
                            navController.navigate(item.route.viewName)
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Image(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = item.title,
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
