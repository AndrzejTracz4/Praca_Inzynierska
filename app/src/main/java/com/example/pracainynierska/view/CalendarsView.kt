package com.example.pracainynierska.view

import BottomMenu
import android.annotation.SuppressLint
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.pracainynierska.viewmodel.LoginViewModel
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarsView(navController: NavController, loginViewModel: LoginViewModel) {

    val focusManager = LocalFocusManager.current

    val selectedDate = remember { mutableStateOf("") }

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
                TopMenu(loginViewModel)
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

                // Wyświetlanie natywnego widoku CalendarView
                AndroidView(
                    factory = { context ->
                        // Zmana na polską lokalizację
                        val locale = Locale("pl", "PL")
                        Locale.setDefault(locale)
                        val config = context.resources.configuration
                        config.setLocale(locale)
                        val localizedContext = context.createConfigurationContext(config)

                        CalendarView(ContextThemeWrapper(localizedContext, R.style.CustomCalendarView)).apply {
                            firstDayOfWeek = Calendar.MONDAY

                            // Ustawienia dla widoku kalendarza
                            setOnDateChangeListener { _, year, month, dayOfMonth ->
                                selectedDate.value = "$dayOfMonth/${month + 1}/$year"
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Selected Date: ${selectedDate.value}")
                }

            }
        }
    }
}