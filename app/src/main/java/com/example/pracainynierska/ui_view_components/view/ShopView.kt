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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.ViewRoutes
import com.example.pracainynierska.dictionary.types.AugmentTypes
import com.example.pracainynierska.ui_view_components.components.AddAugmentButton
import com.example.pracainynierska.ui_view_components.components.CustomAlertDialog
import com.example.pracainynierska.ui_view_components.components.CustomSlider
import com.example.pracainynierska.ui_view_components.components.CustomSliderDefaults
import com.example.pracainynierska.ui_view_components.components.ShopSelectButton
import com.example.pracainynierska.ui_view_components.components.progress
import com.example.pracainynierska.ui_view_components.components.track
import com.example.pracainynierska.view_model.ShopViewModel
import java.util.Locale.Category

class ShopView(shopViewModel: ShopViewModel,
               navController: NavController
) : AbstractView(shopViewModel, navController) {

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    public override fun renderContent(
        innerPadding: PaddingValues
    ) {

        if (false == (viewModel is ShopViewModel)){
            throw Exception("Invalid View Model")
        }

        var selectedCategory by remember { mutableIntStateOf(0) }
        var selectedShopMode by remember { mutableStateOf(AugmentTypes.SHIELD) }
        var sliderValueTime by remember { mutableFloatStateOf(10f) }
        var sliderValueMultiplier by remember { mutableFloatStateOf(20f) }
        var costValue by remember { mutableIntStateOf(0) }
        var isHidden by remember { mutableStateOf(true) }
        val showAlert = remember { mutableStateOf(false) }
        val alertTitleId = remember { mutableIntStateOf(R.string.error) }
        val forward = remember { mutableStateOf(false) }
        val alertMessageId = remember { mutableIntStateOf(0) }
        val playerCategories = viewModel.getPlayerCategories()

        costValue = viewModel.calculateCost(
            type = selectedShopMode,
            validForDays = sliderValueTime.toInt(),
            multiplier = sliderValueMultiplier.toInt()
        )


        val isAffordable = viewModel.checkIfCanAfford(costValue)

        val costTextColor = if (isAffordable) Color.White else Color.Red

        val costText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Normal)) {
                append(stringResource(R.string.cost))
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
                        text = stringResource(R.string.shield_text_button),
                        isSelected = selectedShopMode == AugmentTypes.SHIELD,
                        onClick = {
                            selectedShopMode = AugmentTypes.SHIELD
                            isHidden = true },
                        iconResId = R.drawable.shield
                    )
                    ShopSelectButton(
                        text = stringResource(R.string.booster_text_button),
                        isSelected = selectedShopMode == AugmentTypes.BOOSTER,
                        onClick = {
                            selectedShopMode = AugmentTypes.BOOSTER
                            isHidden = false },
                        iconResId = R.drawable.timeout
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.duration),
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
                        text = stringResource(R.string.multiplier),
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
                    text = stringResource(R.string.category),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column {
                        playerCategories.forEach { category ->
                            ShopSelectButton(
                                text = category.name,
                                isSelected = selectedCategory == category.id,
                                onClick = {
                                    selectedCategory = category.id
                                },
                                iconResId = null
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

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
                        contentDescription = stringResource(R.string.icon_cost_description),
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp)
                    )
                }

                AddAugmentButton(
                    onClick = {
                        viewModel.buyBooster(
                            type = selectedShopMode,
                            validForDays = (sliderValueTime / 10).toInt(),
                            multiplier = sliderValueMultiplier.toInt(),
                            category = "/api/categories/$selectedCategory",
                            price = costValue,
                            onSuccess = {
                                forward.value = true
                                alertTitleId.intValue = R.string.success
                                alertMessageId.intValue = R.string.success_augment_purchase
                                showAlert.value = true
                            },
                            onError = {
                                forward.value = false
                                alertTitleId.intValue = R.string.error
                                alertMessageId.intValue = R.string.error_augment_purchase
                                showAlert.value = true
                            }
                        )
                    }
                )

                CustomAlertDialog(
                    showAlert = showAlert,
                    alertTitleId = alertTitleId.intValue,
                    alertMessageId = alertMessageId.intValue,
                    toForward = forward.value,
                    onConfirmClick = { navController.navigate(ViewRoutes.HOMEPAGE.viewName) }
                )

                Spacer(modifier = Modifier.height(75.dp))
            }
        }
    }
}
