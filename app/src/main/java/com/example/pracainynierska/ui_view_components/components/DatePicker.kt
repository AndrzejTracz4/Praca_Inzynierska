package com.example.pracainynierska.ui_view_components.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerDialog(onDateTimeSelected: (String) -> Unit, onDismissRequest: () -> Unit) {
    val dateState = rememberDatePickerState()
    val formatter = remember { SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()) }
    val context = LocalContext.current

    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                DatePicker(
                    state = dateState,
                    title = { Text("Wybierz datę", color = Color.Black) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    // Sprawdzamy, czy wybrano datę
                    dateState.selectedDateMillis?.let { millis ->
                        // Po wyborze daty otwiera się okno dialogowe z wyborem godziny
                        val timePickerDialog = android.app.TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                selectedHour = hour
                                selectedMinute = minute

                                // Formatujemy wybraną datę i godzinę
                                val date = Date(millis)
                                date.hours = selectedHour
                                date.minutes = selectedMinute
                                val formattedDateTime = formatter.format(date)
                                onDateTimeSelected(formattedDateTime)
                            },
                            selectedHour,
                            selectedMinute,
                            true
                        )
                        timePickerDialog.show()
                    } ?: onDateTimeSelected("Nie wybrano daty")
                }) {
                    Text("Wybierz datę i godzinę")
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = onDismissRequest) {
                    Text("Zamknij")
                }
            }
        }
    }
}
