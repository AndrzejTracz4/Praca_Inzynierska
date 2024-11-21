package com.example.pracainynierska.ui_view_components

import BottomMenu
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.ui.components.ModalDrawer
import com.example.pracainynierska.ui_view_components.components.GradientLevelProgressBar
import com.example.pracainynierska.ui_view_components.components.TopMenu
import com.example.pracainynierska.ui_view_components.components.UserImagePicker
import com.example.pracainynierska.view_model.LoginViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileView(navController: NavController, loginViewModel: LoginViewModel) {
    val user = loginViewModel.user.observeAsState().value
    val userLevel = user?.level ?: 1
    val userEmail = user?.email.toString()
    val userExperience = user?.experience ?: 0.0

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

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedAnimationIndex by remember { mutableIntStateOf(0) }

    val lottieFiles = listOf(
        "app/src/main/res/raw/user_photo_1.json",
        "app/src/main/res/raw/user_photo_2.json",
        "app/src/main/res/raw/user_photo_3.json",
        "app/src/main/res/raw/user_photo_4.json",
        "app/src/main/res/raw/user_photo_5.json",
        "app/src/main/res/raw/user_photo_6.json",
        "app/src/main/res/raw/user_photo_7.json",
        "app/src/main/res/raw/user_photo_8.json"
    )

    loginViewModel.user.observeAsState().value?.let { user ->
        val userPhotoPath = user.userPhotoPath
        selectedAnimationIndex = lottieFiles.indexOf(userPhotoPath).takeIf { it >= 0 } ?: 0
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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF4C0949),
                                Color(0xFF470B93)
                            )
                        )
                    )
                    .padding(16.dp),

                ) {
                Spacer(modifier = Modifier.height(65.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0x19FFFFFF))
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            UserImagePicker(
                                selectedAnimationIndex = selectedAnimationIndex,
                                onImageChanged = { newPhotoPath ->
                                    loginViewModel.updateUserPhotoPath(newPhotoPath)
                                }
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            // Przycisk do zmiany obrazka
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        color = Color(0x19FFFFFF)
                                    )
                                    .clickable {
                                        selectedAnimationIndex = (selectedAnimationIndex + 1) % 7
                                    }
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.change_image_icon),
                                        contentDescription = "Ikona zmiany obrazka",
                                        modifier = Modifier.size(24.dp),
                                        tint = Color.White
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = "Zmień obrazek",
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }

                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = userEmail,
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

                            GradientLevelProgressBar(loginViewModel)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
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

                                Text(
                                    text = "${userExperience.toInt()}/100",
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

                Spacer(modifier = Modifier.weight(1f))

                // Przycisk wylogowania
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            color = Color(0x19FFFFFF)
                        )
                        .clickable {
                            navController.navigate("LoginView")
                        }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.logout),
                            contentDescription = "Ikona wylogowania",
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Wyloguj się",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }


                Spacer(modifier = Modifier.height(75.dp))
            }
        }
    }
}
