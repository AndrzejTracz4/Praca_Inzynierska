package com.example.pracainynierska.ui_view_components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.RankDictionary
import com.example.pracainynierska.ui_view_components.components.GradientLevelProgressBar
import com.example.pracainynierska.ui_view_components.components.UserImagePicker
import com.example.pracainynierska.ui_view_components.view.AbstractView
import com.example.pracainynierska.view_model.ProfileViewModel

class ProfileView(
    profileViewModel: ProfileViewModel,
    navController: NavController,
) : AbstractView(profileViewModel, navController) {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    public override fun renderContent(innerPadding: PaddingValues) {
        if (false == (viewModel is ProfileViewModel)) {
            throw Exception("Invalid View Model")
        }

        val rankDictionary = RankDictionary()

        var userLevel = 1
        var playerExperience = 0f
        var playerBalance = 0
        var playerEmail = ""
        val playerPhotoResId = viewModel.getPhotoResId()
        val playerModel = viewModel.getPlayerModel()
        val player = viewModel.getPlayer()

        val appContext = LocalContext.current

        playerModel.observeAsState().value.let {
            if (it != null) {
                userLevel = it.playerLevel
                playerEmail = it.email
                playerExperience = it.playerExperience.toFloat()
                playerBalance = it.balance
            }
        }

        val userRank = rankDictionary.levelNames[userLevel] ?: "Nieznany poziom"

        Column(
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
                            ),
                    ).padding(16.dp),
        ) {
            Spacer(modifier = Modifier.height(65.dp))

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0x19FFFFFF))
                        .padding(16.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        UserImagePicker(
                            userResId = playerPhotoResId,
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        // Przycisk do zmiany obrazka
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        color = Color(0x19FFFFFF),
                                    ).clickable {
                                        viewModel.changeUserPhoto()
                                    }.padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.change_image_icon),
                                    contentDescription = "Ikona zmiany obrazka",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.White,
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "Zmień obrazek",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = playerEmail,
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

                        GradientLevelProgressBar(playerExperience)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
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

                            Text(
                                text = "${playerExperience.toInt()}/100",
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

            Spacer(modifier = Modifier.weight(1f))

            // Przycisk wylogowania
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            color = Color(0x19FFFFFF),
                        ).clickable {
                            navController.navigate("LoginView")
                        }.padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.logout),
                        contentDescription = "Ikona wylogowania",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Wyloguj się",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            }

            Spacer(modifier = Modifier.height(75.dp))
        }
    }
}
