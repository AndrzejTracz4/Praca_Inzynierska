package com.example.pracainynierska.ui_view_components.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.dictionary.ViewRoutes
import com.example.pracainynierska.ui_view_components.components.CustomCalendar
import com.example.pracainynierska.ui_view_components.components.TaskCard
import com.example.pracainynierska.ui_view_components.components.TaskDetailsDialog
import com.example.pracainynierska.view_model.CalendarsViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarsView(
    calendarsViewModel: CalendarsViewModel,
    navController: NavController,
) : AbstractView(calendarsViewModel, navController) {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    public override fun renderContent(
        innerPadding: PaddingValues
    ) {
        if (false == (viewModel is CalendarsViewModel)) {
            throw Exception("Invalid View Model")
        }

        var selectedDate by remember { mutableStateOf(LocalDate.now()) }
        var selectedTask by remember { mutableStateOf<Task?>(null) }

        var tasks by remember { mutableStateOf(emptyList<Task>()) }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(selectedDate) {
            tasks = viewModel.getTasksByDate(selectedDate.toString())
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF4C0949),
                            Color(0xFF470B93)
                        ),
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset(0f, 0f)
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 0.dp)
            ) {
                CustomCalendar(
                    selectedDate = selectedDate,
                    onDateSelected = { date ->
                        selectedDate = date
                        coroutineScope.launch {
                            tasks = viewModel.getTasksByDate(date.toString())
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 16.dp)
                ) {
                    tasks.forEach { task ->
                        TaskCard(
                            task = task,
                            onClick = { selectedTask = task }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                selectedTask?.let { task ->
                    TaskDetailsDialog(
                        calendarsViewModel = viewModel,
                        task = task,
                        onDismiss = { selectedTask = null },
                        onEdit = {
                            navController.navigate("${ViewRoutes.EDITTASK.viewName}/${task.id}")
                        }
                    )
                }
            }
        }
    }
}
