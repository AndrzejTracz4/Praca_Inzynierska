package com.example.pracainynierska.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pracainynierska.repository.UserRepository
import com.example.pracainynierska.viewmodel.LoginViewModel
import com.example.pracainynierska.viewmodel.LoginViewModelFactory


@Composable
fun GradientLevelProgressBar(userRepository: UserRepository,userUUID: String?) {

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(userRepository)
    )

    var userExperience = 0f

    loginViewModel.user.observeAsState().value.let {
        if (userUUID != null) {
            loginViewModel.fetchUser(userUUID)
        }
        if (it != null) {
            userExperience = it.experience
        }
    }

    // Ograniczenie wartości progress do zakresu 0-100
    val normalizedProgress = userExperience.coerceIn(0f, 100f) / 100f

    // Tworzenie gradientu w zależności od wartości progress
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color.Green, Color.Yellow, Color.Red),
        startX = 0.0f,
        endX = 1000f * normalizedProgress // dostosowanie skali dla efektu gradientu
    )

    // Tło paska
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.LightGray)
    ) {
        // Pasek postępu
        Box(
            modifier = Modifier
                .fillMaxWidth(normalizedProgress) // Skaluje szerokość paska proporcjonalnie do wartości progress
                .height(8.dp)
                .background(gradient)
        )
    }
}