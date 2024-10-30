package com.example.pracainynierska.view.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.animateFloatAsState
import com.example.pracainynierska.viewmodel.LoginViewModel

@Composable
fun GradientLevelProgressBar(loginViewModel: LoginViewModel) {

    var userExperience = 0f

    loginViewModel.user.observeAsState().value.let {
        if (it != null) {
            userExperience = it.experience
        }
    }

    // Ograniczenie wartości progress do zakresu 0-100
    val normalizedProgress = userExperience.coerceIn(0f, 100f) / 100f

    // Animacja wartości progress
    val animatedProgress by animateFloatAsState(
        targetValue = normalizedProgress,
        animationSpec = tween(durationMillis = 1000) // Czas trwania animacji w milisekundach
    )

    // Tworzenie gradientu w zależności od wartości progress
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF72F51E), Color(0xFFF9E80B), Color(0xFFEF090D)),
        startX = 0.0f,
        endX = animatedProgress * 600f
    )

    // Tło paska
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0x11FFFFFF))
    ) {
        // Pasek postępu
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress) // Skaluje szerokość paska proporcjonalnie do wartości progress
                .height(8.dp)
                .background(gradient)
        )
    }
}
