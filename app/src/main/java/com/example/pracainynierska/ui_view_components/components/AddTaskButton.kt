package com.example.pracainynierska.ui_view_components.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pracainynierska.API.model.Category
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.types.TaskTypes
import com.example.pracainynierska.view_model.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CreateTaskButton(
    text: String,
    taskName: String,
    selectedDifficulty: String,
    selectedCategory: Int,
    selectedStartDate: String,
    selectedEndDate: String,
    interval: Int,
    selectedMeasureUnit: String,
    selectedAddTaskMode: String,
    modifier: Modifier = Modifier,
    onTaskCreated: () -> Unit,
    taskViewModel: TaskViewModel,
    taskDescription: String
) {
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessageId by remember { mutableStateOf(0) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showDateErrorDialog by remember { mutableStateOf(false) }
    var taskList by remember { mutableStateOf(emptyList<Task>()) }

    taskViewModel.tasks.observeForever { tasks ->
        taskList = tasks
    }

    Box(
        modifier = modifier
            .height(75.dp)
            .padding(vertical = 4.dp)
            .background(
                color = Color(0x19FFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                val isValid = when (selectedAddTaskMode) {
                    TaskTypes.ONE_TIME -> {
                        taskName.isNotBlank() &&
                                selectedDifficulty.isNotBlank() &&
                                selectedCategory != 0 &&
                                selectedStartDate.isNotBlank() &&
                                selectedEndDate.isNotBlank()
                    }
                    TaskTypes.RECURRING -> {
                        taskName.isNotBlank() &&
                                selectedDifficulty.isNotBlank() &&
                                selectedCategory != 0 &&
                                selectedStartDate.isNotBlank() &&
                                selectedEndDate.isNotBlank() &&
                                selectedMeasureUnit.isNotBlank() &&
                                interval > 0
                    }
                    else -> false
                }

                if (!isValid) {
                    dialogMessageId = R.string.validation_add_task_fields
                    showErrorDialog = true
                } else if (isEndDateBeforeStartDate(selectedStartDate, selectedEndDate)) {
                    dialogMessageId = R.string.validation_date_earlier_that_start
                    showDateErrorDialog = true
                } else {
                    val lastTaskId = taskList.maxOfOrNull { it.id } ?: 0
                    val task = Task(
                        id = lastTaskId + 1,
                        name = taskName,
                        difficulty = selectedDifficulty,
                        category = selectedCategory,
                        startDate = selectedStartDate,
                        endDate = selectedEndDate,
                        interval = if (selectedAddTaskMode == TaskTypes.RECURRING) interval else 0,
                        measureUnit = if (selectedAddTaskMode == TaskTypes.RECURRING) selectedMeasureUnit else "",
                        type = selectedAddTaskMode,
                        status = "Pending",
                        description = taskDescription
                    )

                    taskViewModel.addLocalTask(task)

                    taskViewModel.addTaskViaApi(
                        type = selectedAddTaskMode,
                        name = taskName,
                        description = taskDescription,
                        category = "/api/categories/$selectedCategory",
                        difficulty = selectedDifficulty,
                        startsAt = selectedStartDate,
                        endsAt = selectedEndDate
                    )

                    dialogMessageId = R.string.success_create_task
                    showDialog = true
                }
            }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.plus_square),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                tint = Color.White
            )

            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    }

    // TODO: MAKE ONE FUNCTION FOR DIALOGS
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(R.string.success)) },
            text = { Text(text = stringResource(dialogMessageId)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onTaskCreated()
                        showDialog = false
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text(text = stringResource(R.string.error)) },
            text = { Text(text = stringResource(dialogMessageId)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showErrorDialog = false
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }

    if (showDateErrorDialog) {
        AlertDialog(
            onDismissRequest = { showDateErrorDialog = false },
            title = { Text(text = stringResource(R.string.invalid_date)) },
            text = { Text(text = stringResource(dialogMessageId)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDateErrorDialog = false
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }
}

// Function that checks if the end date is earlier than the start date
fun isEndDateBeforeStartDate(startDate: String, endDate: String): Boolean {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return try {
        val start = dateFormat.parse(startDate)
        val end = dateFormat.parse(endDate)
        end.before(start)
    } catch (e: Exception) {
        false
    }
}
