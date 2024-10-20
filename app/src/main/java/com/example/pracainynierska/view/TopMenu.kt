package com.example.pracainynierska.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopMenu (username: String?) {
    Box(
        modifier = Modifier
            .padding(0.dp)
            .clip(RoundedCornerShape(0.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF070709),  // Początkowy kolor
                        Color(0xFF1B1871)   // Końcowy kolor
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(400f, 400f)  // Ustawienia na prawo-dół
                )
            )
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    // Akcja todo
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
            },
            title = {
                Text(
                    text = username ?: "Loading...",
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
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent // Ustaw przezroczystość, żeby tło nie nadpisywało gradientu
            )
        )
    }
}
