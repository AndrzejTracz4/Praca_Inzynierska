package com.example.pracainynierska.ui_view_components.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun EditTaskButton(
    text: String,
    taskName: String,
    taskToEdit: Task,
    selectedDifficulty: String,
    selectedCategory: Category?,
    selectedStartDate: String,
    selectedEndDate: String,
    interval: Int,
    selectedMeasureUnit: String,
    selectedEditTaskMode: String,
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel,
    taskDescription: String,
    onTaskUpdated: () -> Unit
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
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(
                color = Color(0x19FFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                val isValid = when (selectedEditTaskMode) {
                    TaskTypes.ONE_TIME -> {
                        selectedDifficulty.isNotBlank() &&
                                selectedCategory != null &&
                                selectedStartDate.isNotBlank() &&
                                selectedEndDate.isNotBlank()
                    }

                    TaskTypes.RECURRING -> {
                        selectedDifficulty.isNotBlank() &&
                                selectedCategory != null &&
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
                    // Task update
                    val updatedTask = taskToEdit.copy(
                        name = taskName,
                        difficulty = selectedDifficulty,
                        category = selectedCategory ?: Category(0, "", emptyList()),
                        startDate = selectedStartDate,
                        endDate = selectedEndDate,
                        interval = if (selectedEditTaskMode == TaskTypes.RECURRING) interval else 0,
                        measureUnit = if (selectedEditTaskMode == TaskTypes.RECURRING) selectedMeasureUnit else "",
                        type = selectedEditTaskMode,
                        description = taskDescription
                    )
                    taskViewModel.updateTask(updatedTask)

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
                painter = painterResource(R.drawable.edit),
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

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(R.string.success)) },
            text = { Text(text = stringResource(dialogMessageId)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onTaskUpdated()
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
