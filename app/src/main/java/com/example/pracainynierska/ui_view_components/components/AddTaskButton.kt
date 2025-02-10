package com.example.pracainynierska.ui_view_components.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pracainynierska.API.model.Category
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.TaskDifficulty
import com.example.pracainynierska.dictionary.TaskUnit
import com.example.pracainynierska.dictionary.types.TaskType
import com.example.pracainynierska.view_model.AddTaskViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CreateTaskButton(
    text: String,
    taskName: String,
    selectedDifficulty: TaskDifficulty,
    selectedCategory: Category?,
    selectedStartDate: String,
    selectedEndDate: String,
    interval: Int,
    selectedMeasureUnit: TaskUnit,
    taskType: TaskType,
    modifier: Modifier = Modifier,
    onTaskCreated: () -> Unit,
    addTaskViewModel: AddTaskViewModel,
    taskDescription: String
) {
    val showDialog = remember { mutableStateOf(false) }
    var dialogTitleId by remember { mutableStateOf(0) }
    var dialogMessageId by remember { mutableStateOf(0) }
    var taskList by remember { mutableStateOf(emptyList<Task>()) }

    addTaskViewModel.tasks.observeForever { tasks ->
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
                val isValid = when (taskType) {
                    TaskType.ONE_TIME -> {
                        taskName.isNotBlank() &&
                                selectedCategory != null &&
                                selectedStartDate.isNotBlank() &&
                                selectedEndDate.isNotBlank()
                    }

                    TaskType.RECURRING -> {
                        taskName.isNotBlank() &&
                                selectedCategory != null &&
                                selectedStartDate.isNotBlank() &&
                                selectedEndDate.isNotBlank() &&
                                interval > 0
                    }
                    else -> false
                }

                if (!isValid) {
                    dialogTitleId = R.string.error
                    dialogMessageId = R.string.validation_add_task_fields
                    showDialog.value = true
                } else if (isEndDateBeforeStartDate(selectedStartDate, selectedEndDate)) {
                    dialogTitleId = R.string.invalid_date
                    dialogMessageId = R.string.validation_date_earlier_that_start
                    showDialog.value = true
                } else {
//                    val lastTaskId = taskList.maxOfOrNull { it.id } ?: 0
//                    val task = Task(
//                        id = lastTaskId + 1,
//                        name = taskName,
//                        difficulty = selectedDifficulty,
//                        category = selectedCategory ?: Category(0, "", mutableListOf()),
//                        startDate = selectedStartDate,
//                        endDate = selectedEndDate,
//                        interval = if (selectedAddTaskMode == TaskType.RECURRING.key) interval else 0,
//                        measureUnit = if (selectedAddTaskMode == TaskType.RECURRING.key) selectedMeasureUnit else "",
//                        type = selectedAddTaskMode,
//                        status = "Pending",
//                        description = taskDescription
//                    )

                    addTaskViewModel.add(
                        name = taskName,
                        difficulty = selectedDifficulty,
                        category = selectedCategory ?: Category(0, "", mutableListOf()),
                        startsAt = selectedStartDate,
                        endsAt = selectedEndDate,
                        interval = if (taskType == TaskType.RECURRING) interval else 0,
                        measureUnit = if (taskType == TaskType.RECURRING) selectedMeasureUnit.key else "",
                        type = taskType,
                        description = taskDescription
                    )

//                    taskViewModel.addTaskViaApi(
//                        type = selectedAddTaskMode,
//                        name = taskName,
//                        description = taskDescription,
//                        category = selectedCategory?.let { "/api/categories/${it.id}" } ?: "0",
//                        difficulty = selectedDifficulty,
//                        startsAt = selectedStartDate,
//                        endsAt = selectedEndDate
//                    )

                    dialogTitleId = R.string.success
                    dialogMessageId = R.string.success_create_task
                    showDialog.value = true
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

    CustomAlertDialog(
        showAlert = showDialog,
        alertTitleId = dialogTitleId,
        alertMessageId = dialogMessageId,
        toForward = dialogTitleId == R.string.success,
        onConfirmClick = { onTaskCreated() }
    )
}

// Function that checks if the end date is earlier than the start date
fun isEndDateBeforeStartDate(startDate: String, endDate: String): Boolean {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
    return try {
        val start = dateFormat.parse(startDate)
        val end = dateFormat.parse(endDate)
        if (start == null || end == null) {
            true
        } else {
            end.before(start)
        }
    } catch (e: Exception) {
        Log.e("DEBUG", "Błąd parsowania daty: ${e.message}")
        true
    }
}


