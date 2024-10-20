package com.example.pracainynierska.view

import BottomMenu
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.viewmodel.LoginViewModel
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddTaskView(navController: NavController, loginViewModel: LoginViewModel) {

    val focusManager = LocalFocusManager.current

    var selectedAddTaskMode by remember { mutableStateOf("Jednorazowe") }
    var isHidden by remember { mutableStateOf(true) }
    var taskName by remember { mutableStateOf("") }
    var taskDuration by remember { mutableStateOf("") }
    var taskStartDate by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("Łatwy") }
    var selectedCategory by remember { mutableStateOf("Samorozwój") }


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
                        width = 180.dp
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
                        width = 180.dp
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

                    TextField(
                        value = taskName,
                        onValueChange = { taskName = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp)), // Zaokrąglenie boków TextField
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0x4DFFFFFF),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true
                    )
                }

            }
        }
    }
}

@Composable
fun CustomAddTaskButton(text: String, isSelected: Boolean, onClick: () -> Unit, iconResId: Int, width: Dp) {
    Box(
        modifier = Modifier
            .width(width)
            .height(50.dp)
            .padding(vertical = 4.dp)
            .background(
                color = if (isSelected) Color(0x4DFFFFFF) else Color(0x19FFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
