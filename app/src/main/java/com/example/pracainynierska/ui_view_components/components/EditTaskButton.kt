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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    selectedCategory: String,
    selectedStartDate: String,
    selectedEndDate: String,
    interval: Int,
    selectedMeasureUnit: String,
    selectedEditTaskMode: String,
    modifier: Modifier = Modifier,
    onTaskUpdated: () -> Unit,
    taskViewModel: TaskViewModel,
    taskDescription: String
) {
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
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
                    TaskTypes.JEDNORAZOWE -> {
                        selectedDifficulty.isNotBlank() &&
                                selectedCategory.isNotBlank() &&
                                selectedStartDate.isNotBlank() &&
                                selectedEndDate.isNotBlank()
                    }
                    TaskTypes.CYKLICZNE -> {
                        selectedDifficulty.isNotBlank() &&
                                selectedCategory.isNotBlank() &&
                                selectedStartDate.isNotBlank() &&
                                selectedEndDate.isNotBlank() &&
                                selectedMeasureUnit.isNotBlank() &&
                                interval > 0
                    }
                    else -> false
                }

                if (!isValid) {
                    dialogMessage = "Uzupełnij wszystkie pola."
                    showErrorDialog = true
                } else if (isEndDateBeforeStartDate(selectedStartDate, selectedEndDate)) {
                    dialogMessage = "Data końcowa nie może być wcześniejsza niż data startowa."
                    showDateErrorDialog = true
                } else {
                    // Aktualizacja zadania
                    val updatedTask = taskToEdit.copy(
                        name = taskName,
                        difficulty = selectedDifficulty,
                        category = selectedCategory,
                        startDate = selectedStartDate,
                        endDate = selectedEndDate,
                        interval = if (selectedEditTaskMode == TaskTypes.CYKLICZNE) interval else 0,
                        measureUnit = if (selectedEditTaskMode == TaskTypes.CYKLICZNE) selectedMeasureUnit else "",
                        type = selectedEditTaskMode,
                        description = taskDescription
                    )
                    taskViewModel.updateTask(updatedTask)

                    dialogMessage = "Pomyślnie zaktualizowano zadanie"
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
            title = { Text(text = "Sukces!") },
            text = { Text(text = dialogMessage) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onTaskUpdated()
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text(text = "Błąd") },
            text = { Text(text = dialogMessage) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showErrorDialog = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    if (showDateErrorDialog) {
        AlertDialog(
            onDismissRequest = { showDateErrorDialog = false },
            title = { Text(text = "Błąd daty") },
            text = { Text(text = dialogMessage) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDateErrorDialog = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}
