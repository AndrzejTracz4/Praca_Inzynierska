package com.example.pracainynierska.ui_view_components.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.ViewRoutes
import com.example.pracainynierska.model.Category
import com.example.pracainynierska.model.Statistic
import com.example.pracainynierska.ui_view_components.components.GradientStatsProgressBars
import com.example.pracainynierska.view_model.StatisticViewModel

class StatisticView(viewModel: StatisticViewModel,
                    navController: NavController
) : AbstractView(viewModel, navController) {


    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    public override fun renderContent(
        innerPadding: PaddingValues
    ) {

        var showStatModal by remember { mutableStateOf(false) }
        var showSuccessDialog by remember { mutableStateOf(false) }
        var statName by remember { mutableStateOf("") }
        var selectedIcon by remember { mutableStateOf(R.raw.determination_bar) }
        var error by remember { mutableStateOf(false) }

        val icons = listOf(
            R.raw.determination_bar, R.raw.physical_fitness_bar,
            R.raw.intelligence_bar, R.raw.knowledge_bar
        )

        val statisticsData = remember {
            mutableStateListOf(
                Category("Kategoria 1", listOf(
                    Statistic(6, "Determinacja", 80f),
                    Statistic(7, "Sprawność fizyczna", 65f),
                    Statistic(5, "Inteligencja", 75f),
                    Statistic(8, "Wiedza", 90f)
                )),
//                Category("Kategoria 2", listOf(
//                    Statistic("Cierpliwość", 70f),
//                    Statistic("Kreatywność", 85f),
//                    Statistic("Zdolności przywódcze", 60f)
//                )),
//                Category("Kategoria 3", listOf(
//                    Statistic("Zarządzanie stresem", 50f),
//                    Statistic("Adaptacja do zmian", 90f)
//                )),
//                Category("Kategoria 4", listOf(
//                    Statistic("Komunikacja", 80f),
//                    Statistic("Praca zespołowa", 85f),
//                    Statistic("Rozwiązywanie problemów", 70f)
//                )),
//                Category("Kategoria 5", listOf(
//                    Statistic("Innowacyjność", 90f),
//                    Statistic("Elastyczność", 80f),
//                    Statistic("Zarządzanie czasem", 75f)
//                ))
            )
        }

        var searchQuery by remember { mutableStateOf("") }

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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomButton(
                    text = stringResource(R.string.add_category),
                    onClick = { navController.navigate(ViewRoutes.ADDCATEGORY.viewName) },
                    iconResId = R.drawable.plus,
                    modifier = Modifier
                        .weight(1f)
                )

                Spacer(modifier = Modifier.width(10.dp))

                CustomButton(
                    text = stringResource(R.string.add_statistic),
                    onClick = { showStatModal = true },
                    iconResId = R.drawable.plus,
                    modifier = Modifier
                        .weight(1f)
                )

                if (showStatModal) {
                    Dialog(onDismissRequest = { showStatModal = false }) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                                .padding(16.dp)
                                .background(Color.Gray.copy(alpha = 0.6f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                                    .padding(16.dp)
                            ) {
                                TextField(
                                    value = statName,
                                    onValueChange = { statName = it },
                                    label = { Text(stringResource(R.string.statistic_name)) },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = TextFieldDefaults.textFieldColors(
                                        containerColor = Color.Transparent
                                    )
                                )

                                if (error) {
                                    Text(
                                        text = stringResource(R.string.validation_required_name_statistic),
                                        color = Color.Red,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(stringResource(R.string.change_icon), color = Color.Black)

                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(3),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                ) {
                                    itemsIndexed(icons) { index, iconRes ->
                                        Box(
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .size(24.dp)
                                                .background(
                                                    if (iconRes == selectedIcon) Color.LightGray else Color.Transparent,
                                                    shape = RoundedCornerShape(50.dp)
                                                )
                                                .clickable {
                                                    selectedIcon = iconRes
                                                }
                                        ) {
                                            val composition by rememberLottieComposition(
                                                LottieCompositionSpec.RawRes(iconRes)
                                            )
                                            LottieAnimation(
                                                composition = composition,
                                                iterations = LottieConstants.IterateForever,
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        if (statName.isBlank()) {
                                            error = true
                                        } else {
                                            statName = ""
                                            selectedIcon = R.raw.intelligence_bar
                                            showStatModal = false
                                            error = false
                                            showSuccessDialog = true
                                        }
                                    }) {
                                    Text(stringResource(R.string.create_statistic))
                                }
                            }
                        }
                    }
                }
            }

            if (showSuccessDialog) {
                AlertDialog(
                    onDismissRequest = { showSuccessDialog = false },
                    confirmButton = {
                        Button(onClick = { showSuccessDialog = false }) {
                            Text(stringResource(R.string.ok))
                        }
                    },
                    title = { Text(stringResource(R.string.success)) },
                    text = { Text(stringResource(R.string.success_creation_statistics)) }
                )
            }

            // Search bar
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = {
                    Text(
                        text = stringResource(R.string.search_category),
                        color = Color.White
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White.copy(alpha = 0.2f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            )


            val filteredCategories = if (searchQuery.isNotEmpty()) {
                statisticsData.filter { category ->
                    category.name.contains(searchQuery, ignoreCase = true)
                }
            } else {
                statisticsData
            }

            filteredCategories.forEach { category ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .background(Color(0x19FFFFFF), shape = RoundedCornerShape(10.dp))
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = category.name,
                                fontSize = 18.sp,
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.weight(1f)
                            )

                            IconButton(
                                onClick = { /* TODO */ },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = stringResource(R.string.icon_edit_category_description),
                                    tint = Color.White
                                )
                            }
                        }


                        category.stats.forEachIndexed { index, stat ->
                            val gradient = when (index % 4) {
                                0 -> Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFFFFA726),
                                        Color(0xFFFF7043)
                                    )
                                )

                                1 -> Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF66BB6A),
                                        Color(0xFF43A047)
                                    )
                                )

                                2 -> Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFFAB47BC),
                                        Color(0xFF8E24AA)
                                    )
                                )

                                else -> Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(
                                            0xFF29B6F6
                                        ), Color(0xFF0288D1)
                                    )
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Column {
                                    GradientStatsProgressBars(
                                        stats = listOf(stat.name to stat.progress),
                                        gradients = listOf(gradient),
                                        icons = icons
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
    }

    @Composable
    fun CustomButton(text: String, onClick: () -> Unit, iconResId: Int, modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .height(50.dp)
                .padding(vertical = 4.dp)
                .background(
                    color = Color(0x19FFFFFF),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { onClick() }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}