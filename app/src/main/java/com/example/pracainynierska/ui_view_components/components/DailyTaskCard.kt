package com.example.pracainynierska.ui_view_components.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pracainynierska.API.model.Challenge
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.TaskDifficulty
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyTaskCard(
    challenge: Challenge,
    onClick: (Challenge) -> Unit
) {
    val today = LocalDateTime.now()
    val endOfDay = today.toLocalDate().atTime(23, 59, 59)

    var timeRemaining by remember { mutableStateOf(getTimeRemaining(endOfDay)) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            timeRemaining = getTimeRemaining(endOfDay)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0x14FFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick(challenge) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.daily_task),
                contentDescription = stringResource(R.string.icon_booster_description),
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 12.dp)
                    .weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = challenge.name,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Znika za: $timeRemaining",
                    color = Color(0xFFFFE53B),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${challenge.points} exp",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(
                                Color(0x14FFFFFF),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 2.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(
                                Color(0x14FFFFFF),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = challenge.coins.toString(),
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.coins),
                            contentDescription = stringResource(R.string.icon_balance_description),
                            tint = Color.Unspecified,
                            modifier = Modifier.size(14.dp)
                        )
                    }

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getTimeRemaining(endDateTime: LocalDateTime): String {
    val now = LocalDateTime.now()
    val duration = Duration.between(now, endDateTime)

    if (duration.isNegative) {
        return "Zadanie zakończone"
    }

    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}