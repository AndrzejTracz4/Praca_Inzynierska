package com.example.pracainynierska.view

import BottomMenu
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddTaskView(navController: NavController, loginViewModel: LoginViewModel) {

    val focusManager = LocalFocusManager.current

    var selectedAddTaskMode by remember { mutableStateOf("Jednorazowe") }
    var isHidden by remember { mutableStateOf(true) }
    var taskName by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("Łatwy") }
    var selectedCategory by remember { mutableStateOf("Samorozwój") }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    var showNumberPicker by remember { mutableStateOf(false) }
    var selectedStartDate by remember { mutableStateOf("") }
    var selectedEndDate by remember { mutableStateOf("") }
    val context = LocalContext.current
    var dayCycle by remember { mutableIntStateOf(0) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val scope = rememberCoroutineScope();



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
        .pointerInput(Unit){
            detectTapGestures(onTap = {focusManager.clearFocus()})
        }
    ) {
        Scaffold(
            topBar = {
                TopMenu(
                    loginViewModel = loginViewModel,
                    drawerState = drawerState,
                    onDrawerOpen = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },

            containerColor = Color.Transparent,

            bottomBar = {
                BottomMenu(navController = navController)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ){
                Spacer(modifier = Modifier.height(55.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable(onClick = { navController.navigate("HomepageView") }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.powrot),
                        contentDescription = "Powrót",
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Powrót",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    CustomAddTaskButton(
                        text = "Jednorazowe",
                        isSelected = selectedAddTaskMode == "Jednorazowe",
                        onClick = {
                            selectedAddTaskMode = "Jednorazowe"
                            isHidden = true
                        },
                        iconResId = R.drawable.disposable_icon,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    CustomAddTaskButton(
                        text = "Cykliczne",
                        isSelected = selectedAddTaskMode == "Cykliczne",
                        onClick = {
                            selectedAddTaskMode = "Cykliczne"
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
                        TextField(
                            value = taskName,
                            onValueChange = { taskName = it },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 0.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = Color.White,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center
                            )
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

                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "Co ile dni?",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    //Spacer(modifier = Modifier.height(4.dp))
                    CustomNumberPickerField(
                        text = "",
                        value = dayCycle,
                        onValueChange = { dayCycle = it },
                        onClick = { showNumberPicker = true }
                    )
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
                        CustomAddTaskButton(
                            text = "Łatwy",
                            isSelected = selectedDifficulty == "Łatwe",
                            onClick = { selectedDifficulty = "Łatwe" },
                            iconResId = R.drawable.disposable_icon,
                            modifier = Modifier.weight(1f)
                        )

                        CustomAddTaskButton(
                            text = "Średni",
                            isSelected = selectedDifficulty == "Średni",
                            onClick = { selectedDifficulty = "Średni" },
                            iconResId = R.drawable.cyclical_icon,
                            modifier = Modifier.weight(1f)
                        )

                        CustomAddTaskButton(
                            text = "Trudny",
                            isSelected = selectedDifficulty == "Trudny",
                            onClick = { selectedDifficulty = "Trudny" },
                            iconResId = R.drawable.cyclical_icon,
                            modifier = Modifier.weight(1f)
                        )
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
                        selectedNumber = dayCycle,
                        onNumberSelected = { number ->
                            dayCycle = number
                            showNumberPicker = false
                        },
                        onDismissRequest = { showNumberPicker = false }
                    )
                }
            }
        }
    }
}


@Composable
fun CustomDatePickerField(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    height: Dp = 50.dp
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(vertical = 4.dp)
            .background(
                color = Color(0x4DFFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value.ifEmpty { text },
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun CustomNumberPickerField(
    text: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    onClick: () -> Unit,
    height: Dp = 50.dp
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(vertical = 4.dp)
            .background(
                color = Color(0x4DFFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (value != 0) value.toString() else text,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}
