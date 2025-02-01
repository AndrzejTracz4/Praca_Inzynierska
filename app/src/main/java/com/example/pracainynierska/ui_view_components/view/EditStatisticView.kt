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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import com.example.pracainynierska.API.model.Statistic
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.StatIcon
import com.example.pracainynierska.dictionary.ViewRoutes
import com.example.pracainynierska.ui_view_components.components.CustomAlertDialog
import com.example.pracainynierska.ui_view_components.components.CustomDeleteButton
import com.example.pracainynierska.ui_view_components.components.CustomSubmitStatisticButton
import com.example.pracainynierska.ui_view_components.components.GeneralTextField
import com.example.pracainynierska.view_model.EditStatisticViewModel

class EditStatisticView(
    viewModel: EditStatisticViewModel,
    navController: NavController,
) : AbstractView(viewModel, navController) {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    public override fun renderContent(
        innerPadding: PaddingValues
    ) {

        if (false == viewModel is EditStatisticViewModel) {
            throw IllegalStateException("Invalid view model type")
        }

        var selectedStat by remember { mutableStateOf<Statistic?>(null) }
        var searchQuery by remember { mutableStateOf("") }
        var activeDialogIndex by remember { mutableIntStateOf(-1) }
        var statisticName by remember { mutableStateOf("") }
        val showDeleteAlert = remember { mutableStateOf(false) }
        val showAlert = remember { mutableStateOf(false) }
        val alertTitleId = remember { mutableIntStateOf(R.string.error) }
        val alertMessageId = remember { mutableIntStateOf(R.string.validation_required_name_statistic) }
        val isValid = remember { mutableStateOf(false) }
        val forward = remember { mutableStateOf(false) }

        var selectedIcon by remember { mutableStateOf(StatIcon.DETERMINATION_BAR.iconPath) }

        val availableStats = viewModel.getPlayerStatistics()
        var filteredStats: List<Statistic>?

        if (activeDialogIndex != -1) {
            AlertDialog(
                onDismissRequest = {
                    activeDialogIndex = -1
                    searchQuery = ""
                },
                title = { Text(stringResource(R.string.select_statistic_label)) },
                text = {
                    Column {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            label = { Text(stringResource(R.string.search_statistic)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        filteredStats = availableStats?.filter {
                            it.name.contains(searchQuery, ignoreCase = true)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp)
                        ) {
                            itemsIndexed(filteredStats ?: emptyList()) { index, stat ->
                                TextButton(
                                    onClick = {
                                        selectedStat = stat
                                        statisticName = stat.name
                                        selectedIcon = stat.iconPath
                                        activeDialogIndex = -1
                                        searchQuery = ""
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text(stat.name, color = Color.Black)
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            activeDialogIndex = -1
                            searchQuery = ""
                        }
                    ) {
                        Text(stringResource(R.string.Close))
                    }
                }
            )
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
            Spacer(modifier = Modifier.height(60.dp))

            // Selecting Statistic
            Text(
                text = stringResource(R.string.select_statistic_label),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0x4DFFFFFF))
                    .clickable {
                        activeDialogIndex = 0
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedStat?.name ?: "",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedStat != null) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 1.dp,
                    color = Color.Gray
                )

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
                        onStringChange = {
                            statisticName = it
                        },
                        singleLine = true
                    )
                }

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
            }

            Spacer(modifier = Modifier.weight(1f))

            if (selectedStat != null) {
                CustomDeleteButton(
                    label = stringResource(R.string.delete_statistic),
                    message = stringResource(R.string.confirm_delete_statistic),
                    onDeleteClick = {
                        viewModel.deleteStatistic(
                            id = selectedStat!!.id,
                            onSuccess = {
                                navController.navigate(ViewRoutes.CATEGORIES.viewName) {
                                    popUpTo(
                                        navController.currentBackStackEntry?.destination?.id
                                            ?: return@navigate
                                    ) {
                                        inclusive = true
                                    }
                                }
                            },
                            onError = {
                                alertMessageId.intValue = R.string.error_statistic_delete
                                alertTitleId.intValue = R.string.error
                                showAlert.value = true
                            }
                        )
                    },
                    showAlert = showDeleteAlert
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            CustomSubmitStatisticButton(
                validate = {
                    isValid.value = statisticName.isNotEmpty()
                    alertMessageId.intValue = when {
                        !isValid.value -> R.string.validation_required_name_statistic
                        else -> alertMessageId.intValue
                    }
                },
                label = stringResource(R.string.edit),
                icon = R.drawable.edit,
                onCreateClick = {
                    viewModel.editStatistic(
                        selectedStat!!.id,
                        statisticName,
                        selectedIcon,
                        onSuccess = {
                            forward.value = true
                            alertMessageId.intValue = R.string.success_statistic_update
                            alertTitleId.intValue = R.string.success
                            showAlert.value = true
                        },
                        onError = {
                            forward.value = false
                            alertMessageId.intValue = R.string.error_statistic_update
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
