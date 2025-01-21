package com.example.pracainynierska.ui_view_components.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.ViewRoutes
import com.example.pracainynierska.view_model.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopMenu (
    navController: NavController,
    username: String = "",
    loginViewModel: LoginViewModel? = null,
    drawerState: DrawerState? = null,
    onDrawerOpen: () -> Unit
) {

    val context = LocalContext.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isHomepage = currentDestination?.hierarchy?.any { it.route == ViewRoutes.HOMEPAGE.viewName } == true

    Log.i("TopMenu", "isHomepage: $isHomepage")

    Box(
        modifier = Modifier
            .padding(0.dp)
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF070709),
                        Color(0xFF1B1871)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(400f, 400f)
                )
            )
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    onDrawerOpen()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(R.string.icon_menu_description),
                        tint = Color.White
                    )
                }
            },
            title = {
                Text(
                    text = username,
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp),
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(3f, 1f),
                            blurRadius = 3f
                        )
                    )
                )
            },
            actions = {
                if (!isHomepage) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.icon_back_description),
                            tint = Color.White
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )

    }
}
