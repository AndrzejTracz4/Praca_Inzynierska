package com.example.pracainynierska.ui_view_components.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieConstants

@Composable
fun GradientStatsProgressBars(
    stats: List<Pair<String, Float>>,
    gradients: List<Brush>,
    icons: List<Int>
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp)) {

        stats.forEachIndexed { index, stat ->
            val (label, progress) = stat

            // Selecting the gradient for a given bar based on the index
            val gradient = gradients.getOrNull(index % gradients.size) ?: gradients.first()

            // Normalization of progress (transformation of values to a range of 0-1)
            val normalizedProgress = progress.coerceIn(0f, 100f) / 100f

            val animatedProgress by animateFloatAsState(
                targetValue = normalizedProgress,
                animationSpec = tween(durationMillis = 1000)
            )

            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0x11FFFFFF))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedProgress)
                            .height(12.dp)
                            .background(gradient)
                    )
                }

                Spacer(modifier = Modifier.height(1.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Transparent, shape = RoundedCornerShape(50.dp))
                    ) {
                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(icons[index % icons.size]))
                        val isAnimationPlaying by remember { mutableStateOf(true) }

                        LottieAnimation(
                            composition = composition,
                            iterations = if (isAnimationPlaying) LottieConstants.IterateForever else 1
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = label,
                        fontSize = 13.sp,
                        color = Color.White,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(3f, 1f),
                                blurRadius = 3f
                            )
                        )
                    )


                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "${progress.toInt()} / 100",
                        fontSize = 13.sp,
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
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
