package com.example.pracainynierska.view

import BottomMenu
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.repository.UserRepository
import com.example.pracainynierska.viewmodel.LoginViewModel
import com.example.pracainynierska.viewmodel.LoginViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShopView(navController: NavController, loginViewModel: LoginViewModel) {
    var selectedStat by remember { mutableStateOf("") }
    var sliderValue by remember { mutableStateOf(5f) }

//    var username = ""
//
//    loginViewModel.user.observeAsState().value.let {
//        if (it != null) {
//            username = it.username
//        }
//    }

    Box () {
        Scaffold(
            topBar = {
                TopMenu(loginViewModel)
            },

            containerColor = Color.Transparent,

            bottomBar = {
                BottomMenu(navController = navController)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF5b6d9d))
                    .padding(16.dp)
            ) {
                // Top boxes (Osłona antyredukcjna statystyk and Modyfikator czasowy)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Color(0xFF5b6d9d), shape = RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text(text = "Osłona antyredukcjna statystyk", color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Color(0xFF5b6d9d), shape = RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text(text = "Modyfikator czasowy", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Slider
                Text(
                    text = "Czas trwania",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Slider(
                    value = sliderValue,
                    onValueChange = { sliderValue = it },
                    valueRange = 0f..10f,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = sliderValue.toInt().toString(),
                    color = Color.White,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Statystyki (Determination, Physical fitness, Intelligence, Knowledge)
                Text(
                    text = "Statystyka",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Buttons for stats
                Column {
                    StatButton(
                        text = "Determinacja",
                        isSelected = selectedStat == "Determinacja",
                        onClick = { selectedStat = "Determinacja"},
                        iconResId = R.drawable.determinacja
                    )
                    StatButton(
                        text = "Sprawność fizyczna",
                        isSelected = selectedStat == "Sprawność fizyczna",
                        onClick = { selectedStat = "Sprawność fizyczna" },
                        iconResId = R.drawable.sprawnosc
                    )
                    StatButton(
                        text = "Inteligencja",
                        isSelected = selectedStat == "Inteligencja",
                        onClick = { selectedStat = "Inteligencja" },
                        iconResId = R.drawable.inteligencja
                    )
                    StatButton(
                        text = "Wiedza",
                        isSelected = selectedStat == "Wiedza",
                        onClick = { selectedStat = "Wiedza" },
                        iconResId = R.drawable.wiedza
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Confirmation button at the bottom
                Button(
                    onClick = { /* Action to confirm */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF5b6d9d))
                        .height(60.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Potwierdź",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
@Composable
fun StatButton(text: String, isSelected: Boolean, onClick: () -> Unit, iconResId: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(vertical = 4.dp)
            .background(
                color = if (isSelected) Color(0x4D000000) else Color(0x19000000),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(24.dp), // Size of the icon
                tint = Color.Black // Tint the icon with white color
            )

            Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text

            // Text
            Text(
                text = text,
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}