package com.example.pracainynierska.ui_view_components.view

import android.os.Build
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.API.model.Challenge
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.RankDictionary
import com.example.pracainynierska.dictionary.StatGradient
import com.example.pracainynierska.dictionary.StatIcon
import com.example.pracainynierska.model.FakeData
import com.example.pracainynierska.ui_view_components.components.AugmentCarousel
import com.example.pracainynierska.ui_view_components.components.DailyTaskCard
import com.example.pracainynierska.ui_view_components.components.DailyTaskDetailsDialog
import com.example.pracainynierska.ui_view_components.components.GradientLevelProgressBar
import com.example.pracainynierska.ui_view_components.components.GradientStatProgressBar
import com.example.pracainynierska.ui_view_components.components.UserImagePicker
import com.example.pracainynierska.view_model.HomepageViewModel

class HomepageView(homepageViewModel: HomepageViewModel,
                   navController: NavController,
) : AbstractView(homepageViewModel, navController) {


    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    public override fun renderContent(
        innerPadding: PaddingValues
    ) {

        if (false == (viewModel is HomepageViewModel)){
            throw Exception("Invalid View Model")
        }

        var userLevel = 1
        var playerExperience = 0f
        var playerBalance = 0
        val playerPhotoResId = viewModel.getUserPhotoResId()
        val playerModel = viewModel.getPlayerModel()
        val player = viewModel.getPlayer()
        var playerAugments = viewModel.getPlayerAugments()
        val dailyChallenge by viewModel.dailyChallenge.observeAsState()
        val dailyChallengeStatus by viewModel.dailyChallengeStatus.observeAsState()

        playerModel.observeAsState().value.let {
            if (it != null) {
                userLevel = it.playerLevel
                playerExperience = it.playerExperience.toFloat()
                playerBalance = it.balance
            }
        }

        val userRank = RankDictionary.fromLevel(userLevel)?.displayName ?: stringResource(R.string.unknown_level)

        val stats = player?.playerStatistics?.statistics
            ?.sortedByDescending { it.experience }
            ?.take(4)

        val scrollState = rememberScrollState()
        var showTaskDetailsDialog by remember { mutableStateOf(false) }

        Box(
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
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Spacer(modifier = Modifier.height(0.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0x19FFFFFF))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        UserImagePicker(
                            userResId = playerPhotoResId,
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = userRank,
                                fontSize = 20.sp,
                                color = Color.White,
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color.Black,
                                        offset = Offset(3f, 1f),
                                        blurRadius = 3f
                                    )
                                )
                            )

                            Text(
                                text = stringResource(R.string.level, userLevel),
                                fontSize = 16.sp,
                                color = Color.White,
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color.Black,
                                        offset = Offset(3f, 1f),
                                        blurRadius = 3f
                                    )
                                )
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = stringResource(R.string.balance, playerBalance),
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    style = TextStyle(
                                        shadow = Shadow(
                                            color = Color.Black,
                                            offset = Offset(3f, 1f),
                                            blurRadius = 3f
                                        )
                                    )
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Icon(
                                    painter = painterResource(id = R.drawable.coins),
                                    contentDescription = stringResource(R.string.icon_balance_description),
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            GradientLevelProgressBar(playerExperience)

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = stringResource(R.string.experience),
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    style = TextStyle(
                                        shadow = Shadow(
                                            color = Color.Black,
                                            offset = Offset(3f, 1f),
                                            blurRadius = 3f
                                        )
                                    )
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Text(
                                    text = "${playerExperience.toInt()}/100",
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    style = TextStyle(
                                        shadow = Shadow(
                                            color = Color.Black,
                                            offset = Offset(3f, 1f),
                                            blurRadius = 3f
                                        )
                                    )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0x19FFFFFF))
                        .padding(20.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        stats?.forEachIndexed { index, stat ->
                            val gradient = StatGradient.getGradientByIndex(index).gradient

                            GradientStatProgressBar(
                                iconResId = StatIcon.fromIconPath(stat.iconPath)?.rawResId ?: R.raw.intelligence_bar,
                                name = stat.name,
                                experience = stat.experience ?: 0,
                                gradient = gradient
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }


                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp)
                        .verticalScroll(scrollState)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = true)
                            .heightIn(min = 140.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x19FFFFFF))
                            .padding(horizontal = 10.dp, vertical = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Top
                        ) {
                            Text(
                                text = stringResource(R.string.active_boosters),
                                fontSize = 13.sp,
                                color = Color.White,
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color.Black,
                                        offset = Offset(3f, 1f),
                                        blurRadius = 3f
                                    )
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = 5.dp)
                            )

                            Spacer(modifier = Modifier.height(1.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(10.dp))
                            ){
                                AugmentCarousel(playerAugments)
                            }

                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = true)
                            .heightIn(min = 140.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x19FFFFFF))
                            .padding(horizontal = 10.dp, vertical = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Top
                        ) {
                            Text(
                                text = stringResource(R.string.challenge_of_the_day),
                                fontSize = 13.sp,
                                color = Color.White,
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color.Black,
                                        offset = Offset(3f, 1f),
                                        blurRadius = 3f
                                    )
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = 5.dp)
                            )

                            Spacer(modifier = Modifier.height(1.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.9f)
                                    .clip(RoundedCornerShape(10.dp))
                            ){
                                dailyChallenge?.let {
                                    DailyTaskCard(
                                        challenge = it,
                                        onClick = { showTaskDetailsDialog = true },
                                    )
                                }
                            }
                        }
                    }

                    if (showTaskDetailsDialog) {
                        dailyChallenge?.let {
                            DailyTaskDetailsDialog(
                                challenge = it,
                                status = dailyChallengeStatus ?: "unknown",
                                onAccept = { viewModel.acceptDailyChallenge() },
                                onExecute = { viewModel.completeDailyChallenge() },
                                onDismiss = { showTaskDetailsDialog = false }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}