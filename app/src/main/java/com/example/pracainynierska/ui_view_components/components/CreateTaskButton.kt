package com.example.pracainynierska.ui_view_components.components

import android.app.ActivityManager.TaskDescription
import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pracainynierska.R
import com.example.pracainynierska.model.Task
import com.example.pracainynierska.view_model.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

enum class TaskMode {
    JEDNORAZOWE,
    CYKLICZNE
}

@Composable
fun CreateTaskButton(
    text: String,
    taskName: String,
    selectedDifficulty: String,
    selectedCategory: String,
    selectedStartDate: String,
    selectedEndDate: String,
    interval: Int,
    selectedMeasureUnit: String,
    selectedAddTaskMode: TaskMode,
    modifier: Modifier = Modifier,
    onTaskCreated: () -> Unit,
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
            .padding(vertical = 4.dp)
            .background(
                color = Color(0x19FFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                // Sprawdzenie, czy wszystkie wymagane pola są uzupełnione
                val isValid = when (selectedAddTaskMode) {
                    TaskMode.JEDNORAZOWE -> {
                        taskName.isNotBlank() &&
                                selectedDifficulty.isNotBlank() &&
                                selectedCategory.isNotBlank() &&
                                selectedStartDate.isNotBlank() &&
                                selectedEndDate.isNotBlank()
                    }
                    TaskMode.CYKLICZNE -> {
                        taskName.isNotBlank() &&
                                selectedDifficulty.isNotBlank() &&
                                selectedCategory.isNotBlank() &&
                                selectedStartDate.isNotBlank() &&
                                selectedEndDate.isNotBlank() &&
                                selectedMeasureUnit.isNotBlank() &&
                                interval > 0
                    }
                }

                if (!isValid) {
                    dialogMessage = "Uzupełnij wszystkie pola."
                    showErrorDialog = true
                } else if (isEndDateBeforeStartDate(selectedStartDate, selectedEndDate)) {
                    dialogMessage = "Data końcowa nie może być wcześniejsza niż data startowa."
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
                        interval = if (selectedAddTaskMode == TaskMode.CYKLICZNE) interval else 0,
                        measureUnit = if (selectedAddTaskMode == TaskMode.CYKLICZNE) selectedMeasureUnit else "",
                        mode = selectedAddTaskMode,
                        status = "Pending",
                        description = taskDescription
                    )
                    taskViewModel.addTask(task)

                    dialogMessage = "Pomyślnie utworzono zadanie"
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

    // AlertDialog dla potwierdzenia utworzenia zadania
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Sukces!") },
            text = { Text(text = dialogMessage) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onTaskCreated()
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    // AlertDialog dla błędnych danych
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

    // AlertDialog dla złych dat
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

// Funkcja sprawdzająca, czy data końcowa jest wcześniejsza niż data startowa
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
