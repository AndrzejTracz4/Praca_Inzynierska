package com.example.pracainynierska.ui_view_components.view

import BottomMenu
import com.example.pracainynierska.ui_view_components.components.GradientLevelProgressBar
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.model.FakeData.fakeTask
import com.example.pracainynierska.model.Task
import com.example.pracainynierska.ui.components.ModalDrawer
import com.example.pracainynierska.ui_view_components.components.BoosterCard
import com.example.pracainynierska.ui_view_components.components.BoosterList
import com.example.pracainynierska.ui_view_components.components.DailyTaskCard
import com.example.pracainynierska.ui_view_components.components.DailyTaskDetailsDialog
import com.example.pracainynierska.ui_view_components.components.GradientStatsProgressBars
import com.example.pracainynierska.ui_view_components.components.TaskMode
import com.example.pracainynierska.ui_view_components.components.TopMenu
import com.example.pracainynierska.ui_view_components.components.UserImagePicker
import com.example.pracainynierska.view_model.BoosterViewModel
import com.example.pracainynierska.view_model.HomepageViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomepageView(
    navController: NavController,
    homepageViewModel: HomepageViewModel,
    boosterViewModel: BoosterViewModel
) {

    val focusManager = LocalFocusManager.current
    var userLevel = 1
    var playerExperience = 0f
    var playerBalance = 0
    val playerModel = homepageViewModel.getPlayerModel()
    val player = homepageViewModel.getPlayer()

    playerModel.observeAsState().value.let {
        if (it != null) {
            userLevel = it.playerLevel
            playerExperience = it.playerExperience.toFloat()
            playerBalance = it.balance
        }
    }

    val levelNames = mapOf(
        1 to "Rekrut",
        2 to "Szeregowy",
        3 to "Starszy Szeregowy",
        4 to "Kapral",
        5 to "Starszy Kapral",
        6 to "Plutonowy",
        7 to "Sierżant",
        8 to "Starszy Sierżant",
        9 to "Major Sierżant",
        10 to "Chorąży",
        11 to "Starszy Chorąży",
        12 to "Sztabowy Chorąży",
        13 to "Podporucznik",
        14 to "Porucznik",
        15 to "Starszy Porucznik",
        16 to "Kapitan",
        17 to "Sztabowy Kapitan",
        18 to "Major",
        19 to "Starszy Major",
        20 to "Podpułkownik",
        21 to "Pułkownik",
        22 to "Starszy Pułkownik",
        23 to "Generał Brygady",
        24 to "Generał Dywizji",
        25 to "Generał Broni",
        26 to "Generał Armii",
        27 to "Marszałek",
        28 to "Marszałek Polowy",
        29 to "Naczelny Wódz",
        30 to "Mistrz Strategii"
    )

    val userRank = levelNames[userLevel] ?: "Nieznany poziom"

    val stats = player?.playerStatistics?.statistics?.map{ it.name to it.value.toFloat() } ?: emptyList()

    val gradients = listOf(
        Brush.horizontalGradient(colors = listOf(Color(0xFFFFA726), Color(0xFFFF7043))),
        Brush.horizontalGradient(colors = listOf(Color(0xFF66BB6A), Color(0xFF43A047))),
        Brush.horizontalGradient(colors = listOf(Color(0xFFAB47BC), Color(0xFF8E24AA))),
        Brush.horizontalGradient(colors = listOf(Color(0xFF29B6F6), Color(0xFF0288D1)))
    )

    val icons = listOf(
        R.raw.determination_bar,
        R.raw.physical_fitness_bar,
        R.raw.intelligence_bar,
        R.raw.knowledge_bar
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()
    var showTaskDetailsDialog by remember { mutableStateOf(false) }


    ModalDrawer(navController = navController, drawerState = drawerState) {
        Scaffold(
            topBar = {
                TopMenu(
                    navController = navController,
                    username = player?.name ?: "",
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

                    // użytkownik
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

                            // Miejsce na zdjęcie użytkownika
                            UserImagePicker(playerModel)

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
                                    text = "Poziom $userLevel",
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
                                        text = "Monety: $playerBalance",
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
                                        contentDescription = "Ikona monet",
                                        tint = Color.Unspecified,
                                        modifier = Modifier.size(20.dp)
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
                                        style = TextStyle(
                                            shadow = Shadow(
                                                color = Color.Black,
                                                offset = Offset(3f, 1f),
                                                blurRadius = 3f
                                            )
                                        )
                                    )

                                    Spacer(modifier = Modifier.width(105.dp))

                                    Text(
                                        text = "${playerExperience.toInt()}/100", // Doświadczenie
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

                    // statystyki
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x19FFFFFF))
                            .padding(20.dp)
                    ){
                        GradientStatsProgressBars(
                            stats = stats,
                            gradients = gradients,
                            icons = icons
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp)
                            .verticalScroll(scrollState)
                    ) {
                        Spacer(modifier = Modifier.height(0.dp))

                        Spacer(modifier = Modifier.height(8.dp))

                        // Aktywne boostery
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
                                    text = "Aktywne boostery",
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
                                    BoosterList(boosterViewModel = boosterViewModel)
                                }

                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Zadanie dnia
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
                                    text = "Wyzwanie dnia!",
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
                                    DailyTaskCard(
                                        task = fakeTask,
                                        onClick = { showTaskDetailsDialog = true },
                                    )
                                }
                            }
                        }

                        if (showTaskDetailsDialog) {
                            DailyTaskDetailsDialog(
                                task = fakeTask,
                                onDismiss = { showTaskDetailsDialog = false }
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}