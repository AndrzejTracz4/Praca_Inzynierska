package com.example.pracainynierska.ui_view_components.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AchievementCard(
    title: String,
    description: String,
    exp: Int,
    coins: Int,
    tasksCompleted: Int,
    tasksToDo: Int,
    onClaimClick: () -> Unit,
) {
    val progressValue = tasksCompleted.toFloat() / tasksToDo.toFloat()
    val isClaimable = progressValue >= 1.0f

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = Color(0x19FFFFFF),
                contentColor = Color.White,
            ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "EXP: $exp | Coins: $coins",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(4.dp))

            LinearProgressIndicator(
                progress = progressValue,
                color = Color(0xFF8BC34A),
                trackColor = Color.LightGray,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp)), // Zaokrąglenie rogów paska progresu
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (isClaimable) {
                        onClaimClick()
                    }
                },
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = if (isClaimable) Color(0xFF8BC34A) else Color(0xFF2F2F2F),
                        contentColor = Color.White,
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                enabled = isClaimable,
            ) {
                Text(
                    text = "Odbierz",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White, // Napis zawsze biały
                )
            }
        }
    }
}
