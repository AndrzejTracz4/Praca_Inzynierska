package com.example.pracainynierska.ui_view_components

import BottomMenu
import android.annotation.SuppressLint
import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.model.Task
import com.example.pracainynierska.ui.components.ModalDrawer
import com.example.pracainynierska.ui_view_components.components.TopMenu
import com.example.pracainynierska.view_model.LoginViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarsView(navController: NavController, loginViewModel: LoginViewModel) {
    val focusManager = LocalFocusManager.current
    val selectedDate = remember { mutableStateOf("") }
    val selectedTasks = remember { mutableStateOf<List<Task>?>(null) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF4C0949), Color(0xFF470B93)),
                            start = Offset(0f, Float.POSITIVE_INFINITY),
                            end = Offset(0f, 0f)
                        )
                    )
                    .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(55.dp))

                    // CalendarView
                    AndroidView(
                        factory = { context ->
                            val locale = Locale("pl", "PL")
                            Locale.setDefault(locale)
                            val config = context.resources.configuration
                            config.setLocale(locale)
                            val localizedContext = context.createConfigurationContext(config)

                            CalendarView(ContextThemeWrapper(localizedContext, R.style.CustomCalendarView)).apply {
                                firstDayOfWeek = Calendar.MONDAY
                                setOnDateChangeListener { _, year, month, dayOfMonth ->
                                    val selectedCalendar = Calendar.getInstance().apply {
                                        set(year, month, dayOfMonth)
                                    }

                                    // Format daty
                                    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                    selectedDate.value = dateFormat.format(selectedCalendar.time)

                                    // Pobranie zadania dla wybranej daty
                                    selectedTasks.value = loginViewModel.getTasksForDate(selectedDate.value)

                                    // logi logi
                                    Log.d("SelectedDate", "Wybrana data: ${selectedDate.value}")
                                    Log.d("Tasks", "Znalezione zadania: ${selectedTasks.value}")
                                    Log.d("AllTasks", "Wszystkie zadania: ${loginViewModel.tasks.value}")
                                    Log.d("Tasks", "Wywołano getTasksForDate z datą: ${selectedDate.value}")


                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    selectedTasks.value?.forEach { task ->

                        val iconResId = when (task.category) {
                            "Samorozwój" -> R.drawable.calendar
                            "Ćwiczenia" -> R.drawable.dumbbell_icon
                            "Edukacja" -> R.drawable.calendar
                            "Praca" -> R.drawable.calendar
                            else -> R.drawable.calendar }

                        val backgroundColor = when (task.difficulty) {
                            "Łatwy" -> Color(0xFF3CB043)
                            "Średni" -> Color(0xFFFFFF00)
                            "Trudny" -> Color(0xFFFF0000)
                            else -> Color(0xFF3CB043)
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color(0x4DFFFFFF),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(4.dp)
                        ) {
                            Row {
                                Box(
                                    modifier = Modifier
                                        .width(80.dp)
                                        .height(120.dp)
                                        .background(
                                            color = backgroundColor,
                                            shape = RoundedCornerShape(
                                                topStart = 12.dp,
                                                bottomStart = 12.dp
                                            )
                                        )
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Column {
                                    Text(
                                        text = task.category,
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .background(
                                                Color(0xFF3CB043),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 20.dp, vertical = 2.dp)
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = task.difficulty,
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = task.name,
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(top = 8.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .width(160.dp)
                                                .background(
                                                    color = Color(0x4DFFFFFF),
                                                    shape = RoundedCornerShape(12.dp)
                                                )
                                                .padding(4.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Icon(
                                                    painter = painterResource(id = iconResId),
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                    modifier = Modifier.size(24.dp)
                                                )

                                                Spacer(modifier = Modifier.width(4.dp))

                                                Text(
                                                    text = task.category,
                                                    color = Color.White,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Bold
                                                )

                                                Spacer(modifier = Modifier.width(8.dp))

                                                Icon(
                                                    painter = painterResource(id = R.drawable.double_arrow),
                                                    contentDescription = null,
                                                    tint = Color.Red,
                                                    modifier = Modifier.size(12.dp)
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

//                                        Icon(
//                                            painter = painterResource(id = iconResId),
//                                            contentDescription = null,
//                                            tint = Color.White,
//                                            modifier = Modifier.size(24.dp)
//                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}
