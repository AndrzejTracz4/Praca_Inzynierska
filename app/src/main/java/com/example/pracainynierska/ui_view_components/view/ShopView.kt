package com.example.pracainynierska.ui_view_components.view

import BottomMenu
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.ui.components.ModalDrawer
import com.example.pracainynierska.ui_view_components.components.AddBoosterButton
import com.example.pracainynierska.ui_view_components.components.CustomSlider
import com.example.pracainynierska.ui_view_components.components.CustomSliderDefaults
import com.example.pracainynierska.ui_view_components.components.ShopSelectButton
import com.example.pracainynierska.ui_view_components.components.TopMenu
import com.example.pracainynierska.ui_view_components.components.progress
import com.example.pracainynierska.ui_view_components.components.track
import com.example.pracainynierska.view_model.BoosterViewModel
import com.example.pracainynierska.view_model.LoginViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShopView(
    navController: NavController,
    loginViewModel: LoginViewModel,
    boosterViewModel: BoosterViewModel
) {
    var selectedCategory by remember { mutableStateOf("Determinacja") }
    var selectedShopMode by remember { mutableStateOf("Osłona antyredukcyjna statystyk") }
    var sliderValueTime by remember { mutableFloatStateOf(10f) }
    var sliderValueMultiplier by remember { mutableFloatStateOf(10f) }
    var isHidden by remember { mutableStateOf(true) }
    var costValue by remember { mutableStateOf(5) }
    val startDate = LocalDate.now()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope();


    val costText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Normal)) {
            append("Koszt: ")
        }
        withStyle(style = SpanStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)) {
            append("$costValue")
        }
    }

    ModalDrawer(navController = navController, drawerState = drawerState) {
        Scaffold(
            topBar = {
                TopMenu(
                    navController = navController,
                    loginViewModel = loginViewModel,
                    drawerState = drawerState,
                    onDrawerOpen = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            bottomBar = {
                BottomMenu(navController = navController)
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF4C0949),
                                Color(0xFF470B93)
                            ),
                            start = Offset(0f, Float.POSITIVE_INFINITY),
                            end = Offset(0f, 0f)
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(55.dp))

                    Column {
                        ShopSelectButton(
                            text = "Osłona antyredukcyjna statystyk",
                            isSelected = selectedShopMode == "Osłona antyredukcyjna statystyk",
                            onClick = {
                                selectedShopMode = "Osłona antyredukcyjna statystyk"
                                isHidden = true },
                            iconResId = R.drawable.shield
                        )
                        ShopSelectButton(
                            text = "Modyfikator czasowy",
                            isSelected = selectedShopMode == "Modyfikator czasowy",
                            onClick = {
                                selectedShopMode = "Modyfikator czasowy"
                                isHidden = false },
                            iconResId = R.drawable.timeout
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

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

                    Text(
                        text = "Kategoria",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column {
                        ShopSelectButton(
                            text = "Determinacja",
                            isSelected = selectedCategory == "Determinacja",
                            onClick = {
                                selectedCategory = "Determinacja"
                                costValue = 5 },
                            iconResId = R.drawable.determinacja
                        )
                        ShopSelectButton(
                            text = "Sprawność fizyczna",
                            isSelected = selectedCategory == "Sprawność fizyczna",
                            onClick = {
                                selectedCategory = "Sprawność fizyczna"
                                costValue = 10 },
                            iconResId = R.drawable.sprawnosc
                        )
                        ShopSelectButton(
                            text = "Inteligencja",
                            isSelected = selectedCategory == "Inteligencja",
                            onClick = {
                                selectedCategory = "Inteligencja"
                                costValue = 15 },
                            iconResId = R.drawable.inteligencja
                        )
                        ShopSelectButton(
                            text = "Wiedza",
                            isSelected = selectedCategory == "Wiedza",
                            onClick = {
                                selectedCategory = "Wiedza"
                                costValue = 20 },
                            iconResId = R.drawable.wiedza
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Koszt text
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = costText,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.coins),
                            contentDescription = "Ikona monet",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(20.dp)
                        )
                    }


                    AddBoosterButton(
                        selectedCategory = selectedCategory,
                        selectedShopMode = selectedShopMode,
                        sliderValueTime = sliderValueTime,
                        sliderValueMultiplier = sliderValueMultiplier,
                        costValue = costValue,
                        boosterViewModel = boosterViewModel,
                        startDate = startDate,
                        isActive = true
                    )

                    Spacer(modifier = Modifier.height(75.dp))
                }
            }
        }
    }
}