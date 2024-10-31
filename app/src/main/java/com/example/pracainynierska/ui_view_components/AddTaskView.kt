package com.example.pracainynierska.ui_view_components

import BottomMenu
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.ui.components.ModalDrawer
import com.example.pracainynierska.ui_view_components.components.CustomChooseTaskButton
import com.example.pracainynierska.ui_view_components.components.CustomCreateTaskButton
import com.example.pracainynierska.ui_view_components.components.CustomDatePickerField
import com.example.pracainynierska.ui_view_components.components.CustomMeasurePickerField
import com.example.pracainynierska.ui_view_components.components.CustomNumberPickerField
import com.example.pracainynierska.ui_view_components.components.CustomTaskTextField
import com.example.pracainynierska.ui_view_components.components.DateTimePickerDialog
import com.example.pracainynierska.ui_view_components.components.NumberPickerDialog
import com.example.pracainynierska.ui_view_components.components.TaskMode
import com.example.pracainynierska.ui_view_components.components.TopMenu
import com.example.pracainynierska.view_model.LoginViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddTaskView(navController: NavController, loginViewModel: LoginViewModel) {

    val focusManager = LocalFocusManager.current

    var selectedAddTaskMode by remember { mutableStateOf(TaskMode.JEDNORAZOWE) }
    var isHidden by remember { mutableStateOf(true) }
    var taskName by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    var showNumberPicker by remember { mutableStateOf(false) }
    var selectedMeasureUnit by remember { mutableStateOf("") }
    var showMeasurePicker by remember { mutableStateOf(false) }
    var selectedStartDate by remember { mutableStateOf("") }
    var selectedEndDate by remember { mutableStateOf("") }
    var interval by remember { mutableIntStateOf(0) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()


    ModalDrawer(navController = navController, drawerState = drawerState) {
        Scaffold(
            topBar = {
                TopMenu(
                    navController = navController,
                    loginViewModel = loginViewModel,
                    drawerState = drawerState,
                    onDrawerOpen = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            bottomBar = {
                BottomMenu(navController = navController)
            },
            containerColor = Color.Transparent
        ) {
            Box (
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
                ){
                    Spacer(modifier = Modifier.height(55.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        CustomChooseTaskButton(
                            text = "Jednorazowe",
                            isSelected = selectedAddTaskMode == TaskMode.JEDNORAZOWE,
                            onClick = {
                                selectedAddTaskMode = TaskMode.JEDNORAZOWE
                                isHidden = true
                            },
                            iconResId = R.drawable.disposable_icon,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        CustomChooseTaskButton(
                            text = "Cykliczne",
                            isSelected = selectedAddTaskMode == TaskMode.CYKLICZNE,
                            onClick = {
                                selectedAddTaskMode = TaskMode.CYKLICZNE
                                isHidden = false
                            },
                            iconResId = R.drawable.cyclical_icon,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "Nazwa",
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
                            CustomTaskTextField(
                                taskName = taskName,
                                onTaskNameChange = { taskName = it }
                            )
                        }

                    }

                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "Data rozpoczęcia",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        //Spacer(modifier = Modifier.height(4.dp))
                        CustomDatePickerField(
                            text = "",
                            value = selectedStartDate,
                            onValueChange = { selectedStartDate = it },
                            onClick = { showStartDatePicker = true }
                        )
                    }


                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "Data zakończenia",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        //Spacer(modifier = Modifier.height(4.dp))
                        CustomDatePickerField(
                            text = "",
                            value = selectedEndDate,
                            onValueChange = { selectedEndDate = it },
                            onClick = { showEndDatePicker = true }
                        )

                    }
                if(!isHidden) {
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
                                text = "Interwał",
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
                                text = "Miara",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            CustomMeasurePickerField(
                                selectedMeasureUnit = selectedMeasureUnit,
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
                            text = "Poziom trudności",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CustomChooseTaskButton(
                                text = "Łatwy",
                                isSelected = selectedDifficulty == "Łatwe",
                                onClick = { selectedDifficulty = "Łatwe" },
                                iconResId = R.drawable.disposable_icon,
                                modifier = Modifier.weight(1f)
                            )

                            CustomChooseTaskButton(
                                text = "Średni",
                                isSelected = selectedDifficulty == "Średni",
                                onClick = { selectedDifficulty = "Średni" },
                                iconResId = R.drawable.cyclical_icon,
                                modifier = Modifier.weight(1f)
                            )

                            CustomChooseTaskButton(
                                text = "Trudny",
                                isSelected = selectedDifficulty == "Trudny",
                                onClick = { selectedDifficulty = "Trudny" },
                                iconResId = R.drawable.cyclical_icon,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "Kategorie",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        CustomChooseTaskButton(
                            text = "Samorozwój",
                            isSelected = selectedCategory == "Samorozwój",
                            onClick = { selectedCategory = "Samorozwój" },
                            iconResId = R.drawable.disposable_icon,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        CustomChooseTaskButton(
                            text = "Ćwiczenia",
                            isSelected = selectedCategory == "Ćwiczenia",
                            onClick = { selectedCategory = "Ćwiczenia" },
                            iconResId = R.drawable.disposable_icon,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        CustomChooseTaskButton(
                            text = "Edukacja",
                            isSelected = selectedCategory == "Edukacja",
                            onClick = { selectedCategory = "Edukacja" },
                            iconResId = R.drawable.disposable_icon,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        CustomChooseTaskButton(
                            text = "Praca",
                            isSelected = selectedCategory == "Praca",
                            onClick = { selectedCategory = "Praca" },
                            iconResId = R.drawable.disposable_icon,
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                    }

                    Column(modifier = Modifier.padding(8.dp)) {
                    CustomCreateTaskButton(
                        text = "Utwórz",
                        taskName = taskName,
                        selectedDifficulty = selectedDifficulty,
                        selectedCategory = selectedCategory,
                        selectedStartDate = selectedStartDate,
                        selectedEndDate = selectedEndDate,
                        interval = interval,
                        selectedMeasureUnit = selectedMeasureUnit,
                        selectedAddTaskMode = selectedAddTaskMode, // Przekaż tryb
                        modifier = Modifier.fillMaxWidth(),
                        onTaskCreated = {
                            taskName = ""
                            selectedDifficulty = ""
                            selectedCategory = ""
                            selectedStartDate = ""
                            selectedEndDate = ""
                            interval = 0
                            selectedMeasureUnit = ""
                        }
                    )
                    }
                    
                    Spacer(modifier = Modifier.height(90.dp))

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
        }
    }
}