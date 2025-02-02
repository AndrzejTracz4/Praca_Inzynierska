package com.example.pracainynierska.ui_view_components.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.StatIcon
import com.example.pracainynierska.dictionary.ViewRoutes
import com.example.pracainynierska.ui_view_components.components.CustomAlertDialog
import com.example.pracainynierska.ui_view_components.components.CustomSubmitStatisticButton
import com.example.pracainynierska.ui_view_components.components.GeneralTextField
import com.example.pracainynierska.view_model.AddStatisticViewModel

class AddStatisticView(
    viewModel: AddStatisticViewModel,
    navController: NavController,
) : AbstractView(viewModel, navController) {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    public override fun renderContent(
        innerPadding: PaddingValues
    ) {

        if (false == viewModel is AddStatisticViewModel) {
            throw IllegalStateException("Invalid view model type")
        }

        var statisticName by remember { mutableStateOf("") }
        var isStatValid by remember { mutableStateOf(false) }
        val showAlert = remember { mutableStateOf(false) }
        val alertTitleId = remember { mutableIntStateOf(R.string.error) }
        val alertMessageId = remember { mutableIntStateOf(R.string.validation_required_name_statistic) }
        val isValid = remember { mutableStateOf(false) }
        val forward = remember { mutableStateOf(false) }

        var selectedIcon by remember { mutableStateOf(StatIcon.DETERMINATION_BAR.iconPath) }

        LaunchedEffect(statisticName) {
            isStatValid = statisticName.isNotEmpty()
        }

        Column(
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(55.dp))

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = stringResource(R.string.name),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0x4DFFFFFF)),
                    contentAlignment = Alignment.Center
                ) {
                    GeneralTextField(
                        string = statisticName,
                        onStringChange = { statisticName = it },
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.change_icon),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    itemsIndexed(StatIcon.entries) { index, statIcon ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(32.dp)
                                .background(
                                    if (statIcon.iconPath == selectedIcon) Color(0x4DFFFFFF) else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    selectedIcon = statIcon.iconPath
                                    isStatValid = statisticName.isNotEmpty()
                                }
                        ) {
                            val composition by rememberLottieComposition(
                                LottieCompositionSpec.RawRes(statIcon.rawResId)
                            )

                            val isAnimationPlaying = statIcon.iconPath == selectedIcon

                            LottieAnimation(
                                composition = composition,
                                iterations = if (isAnimationPlaying) LottieConstants.IterateForever else 1,
                            )
                        }
                    }

                }

            }

            Spacer(modifier = Modifier.weight(1f))

            CustomSubmitStatisticButton(
                validate = {
                    isValid.value = statisticName.isNotEmpty()
                    alertMessageId.intValue = when {
                        !isValid.value -> R.string.validation_required_name_statistic
                        else -> alertMessageId.intValue
                    }
                },
                label = stringResource(R.string.create),
                icon = R.drawable.plus_square,
                onCreateClick = {
                    viewModel.addStatistic(
                        statisticName,
                        selectedIcon,
                        onSuccess = {
                            forward.value = true
                            alertMessageId.intValue = R.string.success_statistic_create
                            alertTitleId.intValue = R.string.success
                            showAlert.value = true
                        },
                        onError = {
                            forward.value = false
                            alertMessageId.intValue = R.string.error_statistic_create
                            alertTitleId.intValue = R.string.error
                            showAlert.value = true
                        }
                    )
                },
                showAlert = showAlert,
                isStatValid = statisticName.isNotEmpty(),
            )

            CustomAlertDialog(
                showAlert = showAlert,
                alertTitleId = alertTitleId.intValue,
                alertMessageId = alertMessageId.intValue,
                toForward = forward.value && isValid.value,
                onConfirmClick = { navController.navigate(ViewRoutes.CATEGORIES.viewName) }
            )

            Spacer(modifier = Modifier.height(75.dp))
        }
    }
}