package com.example.pracainynierska.ui_view_components.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.types.ShopTypes
import com.example.pracainynierska.ui_view_components.components.AddAugmentButton
import com.example.pracainynierska.ui_view_components.components.CustomSlider
import com.example.pracainynierska.ui_view_components.components.CustomSliderDefaults
import com.example.pracainynierska.ui_view_components.components.ShopSelectButton
import com.example.pracainynierska.ui_view_components.components.progress
import com.example.pracainynierska.ui_view_components.components.track
import com.example.pracainynierska.view_model.ShopViewModel

class ShopView(shopViewModel: ShopViewModel,
               navController: NavController
) : AbstractView(shopViewModel, navController) {

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    public override fun renderContent(
        innerPadding: PaddingValues
    ) {

        var selectedCategory by remember { mutableStateOf("Determinacja") }
        var selectedShopMode by remember { mutableStateOf(ShopTypes.SHIELD) }
        var sliderValueTime by remember { mutableFloatStateOf(10f) }
        var validForDays by remember { mutableStateOf((sliderValueTime / 10).toInt()) }
        var sliderValueMultiplier by remember { mutableFloatStateOf(20f) }
        var costValue by remember { mutableStateOf(0) }

        var isHidden by remember { mutableStateOf(true) }

        if (false == (viewModel is ShopViewModel)){
            throw Exception("Invalid View Model")
        }

        costValue = viewModel.calculateCost(
            type = selectedShopMode,
            validForDays = validForDays,
            multiplier = sliderValueMultiplier.toInt()
        )

        val isAffordable = viewModel.checkIfCanAfford(costValue)

        val costTextColor = if (isAffordable) Color.White else Color.Red

        val costText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Normal)) {
                append("Koszt: ")
            }
            withStyle(style = SpanStyle(color = costTextColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)) {
                append("$costValue")
            }
        }

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
                        isSelected = selectedShopMode == ShopTypes.SHIELD,
                        onClick = {
                            selectedShopMode = ShopTypes.SHIELD
                            isHidden = true },
                        iconResId = R.drawable.shield
                    )
                    ShopSelectButton(
                        text = "Modyfikator czasowy",
                        isSelected = selectedShopMode == ShopTypes.BOOSTER,
                        onClick = {
                            selectedShopMode = ShopTypes.BOOSTER
                            isHidden = false },
                        iconResId = R.drawable.timeout
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Czas trwania (d)",
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
                        text = "Mnożnik (x)",
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
                        valueRange = 20f..100f,
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
                            selectedCategory = "Determinacja" },
                        iconResId = R.drawable.determinacja
                    )
                    ShopSelectButton(
                        text = "Sprawność fizyczna",
                        isSelected = selectedCategory == "Sprawność fizyczna",
                        onClick = {
                            selectedCategory = "Sprawność fizyczna" },
                        iconResId = R.drawable.sprawnosc
                    )
                    ShopSelectButton(
                        text = "Inteligencja",
                        isSelected = selectedCategory == "Inteligencja",
                        onClick = {
                            selectedCategory = "Inteligencja" },
                        iconResId = R.drawable.inteligencja
                    )
                    ShopSelectButton(
                        text = "Wiedza",
                        isSelected = selectedCategory == "Wiedza",
                        onClick = {
                            selectedCategory = "Wiedza" },
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


                AddAugmentButton(
                    onClick = {
                        require(viewModel is ShopViewModel) { "Invalid View Model" }
                        viewModel.buyBooster(
                            type = selectedShopMode,
                            validForDays = (sliderValueTime / 10).toInt(),
                            multiplier = sliderValueMultiplier.toInt(),
                            //category = selectedCategory,
                            category = "/api/categories/4",
                            price = costValue
                        )
                    }
                )

                Spacer(modifier = Modifier.height(75.dp))
            }
        }
    }
}
