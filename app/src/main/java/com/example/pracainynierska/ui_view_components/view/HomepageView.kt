package com.example.pracainynierska.ui_view_components.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.RankDictionary
import com.example.pracainynierska.model.FakeData
import com.example.pracainynierska.ui_view_components.components.AugmentList
import com.example.pracainynierska.ui_view_components.components.DailyTaskCard
import com.example.pracainynierska.ui_view_components.components.DailyTaskDetailsDialog
import com.example.pracainynierska.ui_view_components.components.GradientLevelProgressBar
import com.example.pracainynierska.ui_view_components.components.GradientStatsProgressBars
import com.example.pracainynierska.ui_view_components.components.UserImagePicker
import com.example.pracainynierska.view_model.HomepageViewModel

class HomepageView(
    homepageViewModel: HomepageViewModel,
    navController: NavController,
) : AbstractView(homepageViewModel, navController) {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    public override fun renderContent(innerPadding: PaddingValues) {
        if (false == (viewModel is HomepageViewModel)) {
            throw Exception("Invalid View Model")
        }

        val rankDictionary = RankDictionary()

        var userLevel = 1
        var playerExperience = 0f
        var playerBalance = 0
        val playerPhotoResId = viewModel.getPhotoResId()
        val playerModel = viewModel.getPlayerModel()
        val player = viewModel.getPlayer()

        playerModel.observeAsState().value.let {
            if (it != null) {
                userLevel = it.playerLevel
                playerExperience = it.playerExperience.toFloat()
                playerBalance = it.balance
            }
        }

        val userRank = rankDictionary.levelNames[userLevel] ?: "Nieznany poziom"

        val stats = player?.playerStatistics?.statistics?.map { it.name to it.experience.toFloat() } ?: emptyList()

        val gradients =
            listOf(
                Brush.horizontalGradient(colors = listOf(Color(0xFFFFA726), Color(0xFFFF7043))),
                Brush.horizontalGradient(colors = listOf(Color(0xFF66BB6A), Color(0xFF43A047))),
                Brush.horizontalGradient(colors = listOf(Color(0xFFAB47BC), Color(0xFF8E24AA))),
                Brush.horizontalGradient(colors = listOf(Color(0xFF29B6F6), Color(0xFF0288D1))),
            )

        val icons =
            listOf(
                R.raw.determination_bar,
                R.raw.physical_fitness_bar,
                R.raw.intelligence_bar,
                R.raw.knowledge_bar,
            )

        val scrollState = rememberScrollState()
        var showTaskDetailsDialog by remember { mutableStateOf(false) }

        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        brush =
                            Brush.linearGradient(
                                colors =
                                    listOf(
                                        Color(0xFF4C0949),
                                        Color(0xFF470B93),
                                    ),
                                start = Offset(0f, Float.POSITIVE_INFINITY),
                                end = Offset(0f, 0f),
                            ),
                    ).padding(innerPadding),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(12.dp),
            ) {
                Spacer(modifier = Modifier.height(0.dp))

                // użytkownik
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x19FFFFFF))
                            .padding(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        UserImagePicker(
                            userResId = playerPhotoResId,
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = userRank,
                                fontSize = 20.sp,
                                color = Color.White,
                                style =
                                    TextStyle(
                                        shadow =
                                            Shadow(
                                                color = Color.Black,
                                                offset = Offset(3f, 1f),
                                                blurRadius = 3f,
                                            ),
                                    ),
                            )

                            Text(
                                text = "Poziom $userLevel",
                                fontSize = 16.sp,
                                color = Color.White,
                                style =
                                    TextStyle(
                                        shadow =
                                            Shadow(
                                                color = Color.Black,
                                                offset = Offset(3f, 1f),
                                                blurRadius = 3f,
                                            ),
                                    ),
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Monety: $playerBalance",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    style =
                                        TextStyle(
                                            shadow =
                                                Shadow(
                                                    color = Color.Black,
                                                    offset = Offset(3f, 1f),
                                                    blurRadius = 3f,
                                                ),
                                        ),
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Icon(
                                    painter = painterResource(id = R.drawable.coins),
                                    contentDescription = "Ikona monet",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(20.dp),
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            GradientLevelProgressBar(playerExperience) // Procent doświadczenia

                            Spacer(modifier = Modifier.height(4.dp))

                            Row {
                                Text(
                                    text = "Doświadczenie",
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    style =
                                        TextStyle(
                                            shadow =
                                                Shadow(
                                                    color = Color.Black,
                                                    offset = Offset(3f, 1f),
                                                    blurRadius = 3f,
                                                ),
                                        ),
                                )

                                Spacer(modifier = Modifier.width(105.dp))

                                Text(
                                    text = "${playerExperience.toInt()}/100", // Doświadczenie
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    style =
                                        TextStyle(
                                            shadow =
                                                Shadow(
                                                    color = Color.Black,
                                                    offset = Offset(3f, 1f),
                                                    blurRadius = 3f,
                                                ),
                                        ),
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // statystyki
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x19FFFFFF))
                            .padding(20.dp),
                ) {
                    GradientStatsProgressBars(
                        stats = stats,
                        gradients = gradients,
                        icons = icons,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(0.dp)
                            .verticalScroll(scrollState),
                ) {
                    Spacer(modifier = Modifier.height(0.dp))

                    Spacer(modifier = Modifier.height(8.dp))

                    // Aktywne boostery
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .weight(1f, fill = true)
                                .heightIn(min = 140.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0x19FFFFFF))
                                .padding(horizontal = 10.dp, vertical = 1.dp),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Top,
                        ) {
                            Text(
                                text = "Aktywne boostery",
                                fontSize = 13.sp,
                                color = Color.White,
                                style =
                                    TextStyle(
                                        shadow =
                                            Shadow(
                                                color = Color.Black,
                                                offset = Offset(3f, 1f),
                                                blurRadius = 3f,
                                            ),
                                    ),
                                modifier =
                                    Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(vertical = 5.dp),
                            )

                            Spacer(modifier = Modifier.height(1.dp))

                            Box(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.9f)
                                        .clip(RoundedCornerShape(10.dp)),
                            ) {
                                if (false == (viewModel is HomepageViewModel)) {
                                    throw Exception("Invalid View Model")
                                }
                                AugmentList(viewModel.getAugmentsList())
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Zadanie dnia
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .weight(1f, fill = true)
                                .heightIn(min = 140.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0x19FFFFFF))
                                .padding(horizontal = 10.dp, vertical = 1.dp),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Top,
                        ) {
                            Text(
                                text = "Wyzwanie dnia!",
                                fontSize = 13.sp,
                                color = Color.White,
                                style =
                                    TextStyle(
                                        shadow =
                                            Shadow(
                                                color = Color.Black,
                                                offset = Offset(3f, 1f),
                                                blurRadius = 3f,
                                            ),
                                    ),
                                modifier =
                                    Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(vertical = 5.dp),
                            )

                            Spacer(modifier = Modifier.height(1.dp))

                            Box(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.9f)
                                        .clip(RoundedCornerShape(10.dp)),
                            ) {
                                DailyTaskCard(
                                    task = FakeData.fakeTask,
                                    onClick = { showTaskDetailsDialog = true },
                                )
                            }
                        }
                    }

                    if (showTaskDetailsDialog) {
                        DailyTaskDetailsDialog(
                            task = FakeData.fakeTask,
                            onDismiss = { showTaskDetailsDialog = false },
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}
