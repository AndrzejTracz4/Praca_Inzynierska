package com.example.pracainynierska.ui_view_components.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalendar(
    selectedDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDay by remember { mutableStateOf(selectedDate) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                Text("<", fontWeight = FontWeight.Bold, color = Color.White)
            }
            Text(
                text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentMonth.year,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                Text(">", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        val daysOfWeek = listOf("Pn", "Wt", "Śr", "Cz", "Pt", "So", "Nd")
        LazyVerticalGrid(columns = GridCells.Fixed(7), content = {
            items(daysOfWeek) { day ->
                Text(
                    text = day,
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color.Transparent),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        })

        val days = remember(currentMonth) { generateDaysOfMonth(currentMonth) }
        LazyVerticalGrid(columns = GridCells.Fixed(7), content = {
            items(days) { date ->
                if (date == null) {
                    Box(modifier = Modifier.size(40.dp))
                } else {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                if (date == selectedDay) Color(0xFF6200EE) else Color.Transparent,
                                shape = MaterialTheme.shapes.medium
                            )
                            .clickable {
                                selectedDay = date
                                onDateSelected(date)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = date.dayOfMonth.toString(),
                            color = if (date == selectedDay) Color.White else Color.LightGray
                        )
                    }
                }
            }
        })
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateDaysOfMonth(yearMonth: YearMonth): List<LocalDate?> {
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfMonth = yearMonth.atDay(1).dayOfWeek.value - 1
    return List(firstDayOfMonth) { null } + (1..daysInMonth).map { yearMonth.atDay(it) }
}
