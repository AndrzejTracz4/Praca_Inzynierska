package com.example.pracainynierska.view

import BottomMenu
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.repository.UserRepository
import com.example.pracainynierska.viewmodel.LoginViewModel
import com.example.pracainynierska.viewmodel.LoginViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShopView(navController: NavController, loginViewModel: LoginViewModel) {
    var selectedStat by remember { mutableStateOf("Determinacja") }
    var selectedShopMode by remember { mutableStateOf("Oslona") }
    var sliderValueTime by remember { mutableFloatStateOf(10f) }
    var sliderValueMultiplier by remember { mutableFloatStateOf(10f) }
    var isHidden by remember { mutableStateOf(true) }
    var costValue by remember { mutableStateOf(5) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val scope = rememberCoroutineScope();


    val costText = buildAnnotatedString {
        // Dodaj normalny tekst
        withStyle(style = SpanStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Normal)) {
            append("Koszt: ")
        }
        // Dodaj pogrubiony tekst z dynamiczną wartością
        withStyle(style = SpanStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)) {
            append("$costValue C")
        }
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF4C0949), // Bottom color
                        Color(0xFF470B93)  // Top color
                    ),
                    start = Offset(0f, Float.POSITIVE_INFINITY), // Start at the bottom
                    end = Offset(0f, 0f)  // End at the top
                )
            )
    ) {
        Scaffold(
            topBar = {
                TopMenu(
                    loginViewModel = loginViewModel,
                    drawerState = drawerState,
                    onDrawerOpen = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },

            containerColor = Color.Transparent,

            bottomBar = {
                BottomMenu(navController = navController)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(55.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp) // Odstęp pod przyciskiem
                        .clickable(onClick = { navController.navigate("HomepageView") }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp), // Ustaw rozmiar ikony
                        painter = painterResource(id = R.drawable.powrot), // Użyj odpowiedniej ikony strzałki
                        contentDescription = "Powrót",
                        tint = Color.White // Kolor ikony
                    )

                    Spacer(modifier = Modifier.width(4.dp)) // Odstęp między ikoną a tekstem

                    Text(
                        text = "Powrót",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column {
                    CustomButton(
                        text = "Osłona antyredukcjna statystyk",
                        isSelected = selectedShopMode == "Oslona",
                        onClick = {
                            selectedShopMode = "Oslona"
                            isHidden = true },
                        iconResId = R.drawable.oslona
                    )
                    CustomButton(
                        text = "Modyfikator czasowy",
                        isSelected = selectedShopMode == "Modyfikator",
                        onClick = {
                            selectedShopMode = "Modyfikator"
                            isHidden = false },
                        iconResId = R.drawable.modyfikator
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Slider
                Text(
                    text = "Czas trwania",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))

                CustomSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp),
                    value = sliderValueTime,
                    onValueChange = {
                        sliderValueTime = it
                    },
                    valueRange = 10f..100f,
                    gap = 10,
                    showIndicator = true,
                    thumb = { thumbValue ->
                        CustomSliderDefaults.Thumb(
                            thumbValue = (thumbValue /10).toString(),
                            color = Color.Transparent,
                            size = 30.dp,
                            modifier = Modifier.background(
                                Color(0xFF32A6F9),
                                shape = CircleShape
                            )
                        )
                    },
                    track = { sliderState ->
                        Box(
                            modifier = Modifier
                                .track()
                                .border(
                                    width = 1.dp,
                                    color = Color(0x19FFFFFF),
                                    shape = CircleShape
                                )
                                .background(Color(0x19FFFFFF))
                                .padding(3.5.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Box(
                                modifier = Modifier
                                    .progress(sliderState = sliderState)
                                    .background(
                                        Color(0xFF32A6F9)
                                    )
                            )
                        }
                    }
                )

                // Mnożnik
                if (!isHidden) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Mnożnik",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    CustomSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp),
                        value = sliderValueMultiplier,
                        onValueChange = {
                            sliderValueMultiplier = it
                        },
                        valueRange = 10f..100f,
                        gap = 10,
                        showIndicator = true,
                        thumb = { thumbValue ->
                            CustomSliderDefaults.Thumb(
                                thumbValue = (thumbValue /10).toString(),
                                color = Color.Transparent,
                                size = 30.dp,
                                modifier = Modifier.background(
                                    Color(0xFF32A6F9),
                                    shape = CircleShape
                                )
                            )
                        },
                        track = { sliderState ->
                            Box(
                                modifier = Modifier
                                    .track()
                                    .border(
                                        width = 1.dp,
                                        color = Color(0x19FFFFFF),
                                        shape = CircleShape
                                    )
                                    .background(Color(0x19FFFFFF))
                                    .padding(3.5.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Box(
                                    modifier = Modifier
                                        .progress(sliderState = sliderState)
                                        .background(
                                            Color(0xFF32A6F9)
                                        )
                                )
                            }
                        }
                    )
                }

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
                    CustomButton(
                        text = "Determinacja",
                        isSelected = selectedStat == "Determinacja",
                        onClick = {
                            selectedStat = "Determinacja"
                                  costValue = 5 },
                        iconResId = R.drawable.determinacja
                    )
                    CustomButton(
                        text = "Sprawność fizyczna",
                        isSelected = selectedStat == "Sprawność fizyczna",
                        onClick = {
                            selectedStat = "Sprawność fizyczna"
                                  costValue = 10 },
                        iconResId = R.drawable.sprawnosc
                    )
                    CustomButton(
                        text = "Inteligencja",
                        isSelected = selectedStat == "Inteligencja",
                        onClick = {
                            selectedStat = "Inteligencja"
                                  costValue = 15 },
                        iconResId = R.drawable.inteligencja
                    )
                    CustomButton(
                        text = "Wiedza",
                        isSelected = selectedStat == "Wiedza",
                        onClick = {
                            selectedStat = "Wiedza"
                                  costValue = 20 },
                        iconResId = R.drawable.wiedza
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Koszt text
                Text(
                    text = costText,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )

                // Confirmation button at the bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(vertical = 4.dp)
                        .background(
                            color = Color(0x19FFFFFF),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { /* TODO */ }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Image
                        Icon(
                            painter = painterResource(R.drawable.zakup),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp), // Size of the icon
                            tint = Color.White // Tint the icon with white color
                        )

                        Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text

                        // Text
                        Text(
                            text = "Potwierdź",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier
                                .padding(start = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(75.dp))
            }
        }
    }
}
@Composable
fun CustomButton(text: String, isSelected: Boolean, onClick: () -> Unit, iconResId: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(vertical = 4.dp)
            .background(
                color = if (isSelected) Color(0x4DFFFFFF) else Color(0x19FFFFFF),
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
                modifier = Modifier.size(20.dp), // Size of the icon
                tint = Color.White // Tint the icon with white color
            )

            Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text

            // Text
            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}