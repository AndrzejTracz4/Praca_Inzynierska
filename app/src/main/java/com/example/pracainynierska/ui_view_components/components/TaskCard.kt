package com.example.pracainynierska.ui_view_components.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.TaskDifficulty

@Composable
fun TaskCard(
    task: Task,
    onClick: (Task) -> Unit
) {
    val iconResId = when (task.category) {
        "Samorozwój" -> R.drawable.calendar
        "Ćwiczenia" -> R.drawable.dumbbell_icon
        "Edukacja" -> R.drawable.calendar
        "Praca" -> R.drawable.calendar
        else -> R.drawable.calendar
    }

    val backgroundColor = TaskDifficulty.fromDisplayName(task.difficulty)?.color
        ?: TaskDifficulty.EASY.color

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0x4DFFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(4.dp)
            .clickable { onClick(task) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            bottomStart = 12.dp
                        )
                    )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = task.category,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            Color(0xFF3CB043),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 20.dp, vertical = 2.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = task.difficulty,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = task.name,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(160.dp)
                            .background(
                                color = Color(0x4DFFFFFF),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = iconResId),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = task.category,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Icon(
                                painter = painterResource(id = R.drawable.double_arrow),
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}