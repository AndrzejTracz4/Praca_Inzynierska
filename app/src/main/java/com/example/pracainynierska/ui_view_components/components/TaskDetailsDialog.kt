package com.example.pracainynierska.ui_view_components.components

import TaskDetailsButton
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.TaskDifficulty
import com.example.pracainynierska.dictionary.TaskStatus
import com.example.pracainynierska.dictionary.types.TaskType
import com.example.pracainynierska.view_model.CalendarsViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskDetailsDialog(
    calendarsViewModel: CalendarsViewModel,
    task: Task,
    onDismiss: () -> Unit,
    onEdit: () -> Unit
) {

    val type = TaskType.fromKey(task.type) ?: TaskType.ONE_TIME
    val difficulty = TaskDifficulty.fromKey(task.difficulty) ?: TaskDifficulty.EASY
    val status = TaskStatus.fromKey(task.status) ?: TaskStatus.NEW

    val taskDate: ZonedDateTime? = try {
        ZonedDateTime.parse(task.startsAt)
    } catch (e: Exception) {
        null
    }

    val isToday = taskDate?.toLocalDate() == ZonedDateTime.now().toLocalDate()

    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.task_details),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xDD1C1C1C),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    task.category?.let {
                        Text(
                            text = task.category.name,
                            color = Color(0xFF3CB043),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row {
                        Text(
                            text = stringResource(R.string.difficulty_level),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = difficulty.displayName,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row {
                        Text(
                            text = stringResource(R.string.task_name),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = task.name,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row {
                        Text(
                            text = "${stringResource(R.string.description)}: ",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = task.description,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row {
                        Text(
                            text = stringResource(R.string.begining),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = formatDate(task.startsAt),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Row {
                        Text(
                            text = stringResource(R.string.ending),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = formatDate(task.endsAt),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    if ((status == TaskStatus.NEW || status == TaskStatus.ACCEPTED) && type != TaskType.CHALLENGE && isToday) {
                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TaskDetailsButton(
                                text = stringResource(R.string.execute),
                                color = Color(0xFF3CB043),
                                onClick = {
                                    calendarsViewModel.completeTask(task.id)
                                }
                            )

                            TaskDetailsButton(
                                text = stringResource(R.string.edit),
                                color = Color.Yellow,
                                onClick = onEdit
                            )

                            TaskDetailsButton(
                                text = stringResource(R.string.cancel),
                                color = Color.Red,
                                onClick = onDismiss
                            )
                        }
                    }

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateString: String): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        zonedDateTime.format(formatter)
    } catch (e: DateTimeParseException) {
        "Invalid date"
    }
}
