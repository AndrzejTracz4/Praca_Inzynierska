package com.example.pracainynierska.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pracainynierska.R
import com.example.pracainynierska.repository.UserRepository
import com.example.pracainynierska.viewmodel.LoginViewModel
import com.example.pracainynierska.viewmodel.LoginViewModelFactory
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.LottieAnimation

@Composable
fun GradientStatsProgressBars(loginViewModel: LoginViewModel) {

    // Zmienne do przechowywania wartości statystyk użytkownika
    var userDetermination = 0f
    var userPhysicalFitness = 0f
    var userIntelligence = 0f
    var userKnowledge = 0f

    // Obserwacja zmian w danych użytkownika
    loginViewModel.user.observeAsState().value.let {
        if (it != null) {
            userDetermination = it.determination
            userPhysicalFitness = it.physical_fitness
            userIntelligence = it.intelligence
            userKnowledge = it.knowledge
        }
    }

    // Lista statystyk i ich wartości
    val stats = listOf(
        "Determinacja" to userDetermination,
        "Sprawność fizyczna" to userPhysicalFitness,
        "Inteligencja" to userIntelligence,
        "Wiedza" to userKnowledge
    )

    // Lista gradientów dla pasków postępu
    val gradients = listOf(
        Brush.horizontalGradient(colors = listOf(Color(0xFFFFA726), Color(0xFFFF7043))),
        Brush.horizontalGradient(colors = listOf(Color(0xFF66BB6A), Color(0xFF43A047))),
        Brush.horizontalGradient(colors = listOf(Color(0xFFAB47BC), Color(0xFF8E24AA))),
        Brush.horizontalGradient(colors = listOf(Color(0xFF29B6F6), Color(0xFF0288D1)))
    )

    // Lista plików Lottie dla etykiet
    val icons = listOf(
        R.raw.determination_bar,
        R.raw.physical_fitness_bar,
        R.raw.intelligence_bar,
        R.raw.knowledge_bar
    )

    // Tworzenie kolumny z paskami postępu
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        // Iteracja przez listę statystyk
        stats.forEachIndexed { index, stat ->
            val (label, progress) = stat
            // Wybór gradientu na podstawie indeksu
            val gradient = gradients[index % gradients.size]
            // Normalizacja wartości postępu do zakresu 0-1
            val normalizedProgress = progress.coerceIn(0f, 100f) / 100f
            // Animacja wartości postępu
            val animatedProgress by animateFloatAsState(
                targetValue = normalizedProgress,
                animationSpec = tween(durationMillis = 1000)
            )

            // Tworzenie pojedynczego paska postępu
            Column {
                // Tło paska
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0x11FFFFFF))
                ) {
                    // Pasek postępu z gradientem
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedProgress)
                            .height(12.dp)
                            .background(gradient)
                    )
                }
                Spacer(modifier = Modifier.height(1.dp))
                // Etykieta i wartość postępu
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Box z animacją Lottie
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
