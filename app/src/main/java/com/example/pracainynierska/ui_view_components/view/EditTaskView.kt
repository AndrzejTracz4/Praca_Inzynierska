package com.example.pracainynierska.ui_view_components.view

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.TaskDifficulty
import com.example.pracainynierska.dictionary.TaskUnit
import com.example.pracainynierska.dictionary.ViewRoutes
import com.example.pracainynierska.dictionary.types.TaskType
import com.example.pracainynierska.ui_view_components.components.CustomDatePickerField
import com.example.pracainynierska.ui_view_components.components.CustomMeasurePickerField
import com.example.pracainynierska.ui_view_components.components.CustomNumberPickerField
import com.example.pracainynierska.ui_view_components.components.DateTimePickerDialog
import com.example.pracainynierska.ui_view_components.components.EditTaskButton
import com.example.pracainynierska.ui_view_components.components.GeneralTextField
import com.example.pracainynierska.ui_view_components.components.NumberPickerDialog
import com.example.pracainynierska.ui_view_components.components.SelectTaskButton
import com.example.pracainynierska.view_model.EditTaskViewModel

class EditTaskView(editTaskViewModel: EditTaskViewModel,
                   navController: NavController,
                   private var taskToEdit: Task
) : AbstractView(editTaskViewModel, navController) {


    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    public override fun renderContent(
        innerPadding: PaddingValues
    ) {

        val focusManager = LocalFocusManager.current

        var selectedEditTaskMode by remember { mutableStateOf(taskToEdit.type) }
        var isHidden by remember { mutableStateOf(selectedEditTaskMode == TaskType.ONE_TIME.key) }
        var taskName by remember { mutableStateOf(taskToEdit.name) }
        var selectedDifficulty by remember { mutableStateOf(TaskDifficulty.fromKey(taskToEdit.difficulty)) }
        var selectedCategoryId by remember { mutableIntStateOf(taskToEdit.category?.id ?: 1) }
        var showStartDatePicker by remember { mutableStateOf(false) }
        var showEndDatePicker by remember { mutableStateOf(false) }
        var showNumberPicker by remember { mutableStateOf(false) }
        var selectedMeasureUnit by remember { mutableStateOf(TaskUnit.fromKey(taskToEdit.measureUnit)) }
        var showMeasurePicker by remember { mutableStateOf(false) }
        var selectedStartDate by remember { mutableStateOf(taskToEdit.startsAt) }
        var selectedEndDate by remember { mutableStateOf(taskToEdit.endsAt) }
        var interval by remember { mutableIntStateOf(taskToEdit.interval) }
        var taskDescription by remember { mutableStateOf(taskToEdit.description) }

        val scrollState = rememberScrollState()

        val playerCategories = viewModel.getPlayerCategories()

        if (false == (viewModel is EditTaskViewModel)){
            throw Exception("Invalid View Model")
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
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.height(55.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    SelectTaskButton(
                        text = stringResource(R.string.daily_task),
                        isSelected = selectedEditTaskMode == TaskType.ONE_TIME.key,
                        onClick = {
                            selectedEditTaskMode = TaskType.ONE_TIME.key
                            isHidden = true
                        },
                        iconResId = R.drawable.repeat_single,
                        modifier = Modifier.weight(1f),
                        color = false
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    SelectTaskButton(
                        text = stringResource(R.string.cyclical_task),
                        isSelected = selectedEditTaskMode == TaskType.RECURRING.key,
                        onClick = {
                            selectedEditTaskMode = TaskType.RECURRING.key
                            isHidden = false
                        },
                        iconResId = R.drawable.repeat,
                        modifier = Modifier.weight(1f),
                        color = false
                    )
                }

                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = stringResource(R.string.name),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0x4DFFFFFF)),
                        contentAlignment = Alignment.Center
                    ) {
                        GeneralTextField(
                            string = taskName,
                            onStringChange = { taskName = it },
                            singleLine = true
                        )
                    }
                }

                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = stringResource(R.string.description),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            //.height(55.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0x4DFFFFFF)),
                        contentAlignment = Alignment.Center
                    ) {
                        GeneralTextField(
                            string = taskDescription,
                            onStringChange = { taskDescription = it },
                            singleLine = false
                        )
                    }
                }

                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = stringResource(R.string.start_date),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    CustomDatePickerField(
                        text = "",
                        value = selectedStartDate,
                        onValueChange = { selectedStartDate = it },
                        onClick = { showStartDatePicker = true }
                    )
                }

                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = stringResource(R.string.end_date),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    CustomDatePickerField(
                        text = "",
                        value = selectedEndDate,
                        onValueChange = { selectedEndDate = it },
                        onClick = { showEndDatePicker = true }
                    )
                }

                if (!isHidden) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = stringResource(R.string.interval),
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            CustomNumberPickerField(
                                text = "",
                                value = interval,
                                onValueChange = { interval = it },
                                onClick = { showNumberPicker = true },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = stringResource(R.string.measure),
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            CustomMeasurePickerField(
                                selectedMeasureUnit = selectedMeasureUnit ?: TaskUnit.MINUTES,
                                onMeasureUnitSelected = { unit -> selectedMeasureUnit = unit },
                                showMeasurePicker = showMeasurePicker,
                                setShowMeasurePicker = { showMeasurePicker = it },
                                onClick = { showMeasurePicker = true },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = stringResource(R.string.difficulty_level),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TaskDifficulty.entries.forEach { difficulty ->
                            SelectTaskButton(
                                text = difficulty.displayName,
                                isSelected = selectedDifficulty == difficulty,
                                onClick = { selectedDifficulty = difficulty },
                                iconResId = difficulty.iconResId,
                                modifier = Modifier.weight(1f),
                                color = true
                            )
                        }

                    }
                }

                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = stringResource(R.string.categories),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    playerCategories.forEach{ category ->
                        SelectTaskButton(
                            text = category.name,
                            isSelected = selectedCategoryId == category.id,
                            onClick = { selectedCategoryId = category.id },
                            iconResId = null,
                            modifier = Modifier.fillMaxWidth(),
                            color = false
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

                Column(modifier = Modifier.padding(8.dp)) {
                    EditTaskButton(
                        text = stringResource(R.string.save_changes),
                        taskToEdit = taskToEdit,
                        taskName = taskName,
                        selectedDifficulty = selectedDifficulty ?: TaskDifficulty.EASY,
                        selectedCategory = playerCategories.find { it.id == selectedCategoryId },
                        selectedStartDate = selectedStartDate,
                        selectedEndDate = selectedEndDate,
                        interval = interval,
                        selectedMeasureUnit = selectedMeasureUnit ?: TaskUnit.MINUTES,
                        selectedEditTaskMode = selectedEditTaskMode,
                        editTaskViewModel = viewModel,
                        taskDescription = taskDescription,
                        onTaskUpdated = {
                            Log.d("TaskUpdated", "Zadanie zostało zaktualizowane.")
                            navController.navigate(ViewRoutes.CALENDAR.viewName)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(90.dp))
            }
        }

        if (showStartDatePicker) {
            DateTimePickerDialog(
                onDateTimeSelected = { dateTime ->
                    selectedStartDate = dateTime
                    showStartDatePicker = false
                },
                onDismissRequest = { showStartDatePicker = false }
            )
        }else if (showEndDatePicker){
            DateTimePickerDialog(
                onDateTimeSelected = { dateTime ->
                    selectedEndDate = dateTime
                    showEndDatePicker = false
                },
                onDismissRequest = { showEndDatePicker = false }
            )
        }
        if (showNumberPicker) {
            NumberPickerDialog(
                selectedNumber = interval,
                onNumberSelected = { number ->
                    interval = number
                    showNumberPicker = false
                },
                onDismissRequest = { showNumberPicker = false }
            )
        }
    }
}
