package com.example.pracainynierska.ui_view_components.components

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
import com.example.pracainynierska.view_model.LoginViewModel

@Composable
fun GradientLevelProgressBar(userExperience: Float) {
    val normalizedProgress = userExperience.coerceIn(0f, 100f) / 100f

    val animatedProgress by animateFloatAsState(
        targetValue = normalizedProgress,
        animationSpec = tween(durationMillis = 1000)
    )

    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF72F51E), Color(0xFFF9E80B), Color(0xFFEF090D)),
        startX = 0.0f,
        endX = animatedProgress * 600f
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0x11FFFFFF))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .height(8.dp)
                .background(gradient)
        )
    }
}
