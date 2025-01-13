package com.example.pracainynierska.ui_view_components.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun BottomMenuItem(
    icon: Int,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier =
            modifier
                .fillMaxHeight()
                .clickable(onClick = onClick),
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier =
                Modifier
                    .size(24.dp),
            colorFilter = ColorFilter.tint(Color.White),
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = label,
            style =
                TextStyle(
                    color = Color.White,
                    fontSize = 8.sp,
                    shadow =
                        Shadow(
                            color = Color.Black,
                            offset = Offset(3f, 1f),
                            blurRadius = 3f,
                        ),
                ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

fun navigateIfNotCurrent(
    navController: NavController,
    route: String,
) {
    val currentRoute = navController.currentDestination?.route
    if (currentRoute != route) {
        navController.navigate(route)
    }
}
