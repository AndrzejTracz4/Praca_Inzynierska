package com.example.pracainynierska.ui_view_components.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun NumberPickerDialog(
    range: IntRange = 0..100,
    selectedNumber: Int,
    onNumberSelected: (Int) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "Wybierz liczbę", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.height(200.dp),
                ) {
                    items(range.toList()) { value ->
                        Text(
                            text = value.toString(),
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .clickable { onNumberSelected(value) }
                                    .padding(8.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = if (value == selectedNumber) FontWeight.Bold else FontWeight.Normal,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onDismissRequest) {
                    Text("Zamknij")
                }
            }
        }
    }
}
