package com.example.pracainynierska.ui_view_components.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomMeasurePickerField(
    selectedMeasureUnit: String,
    onMeasureUnitSelected: (String) -> Unit,
    showMeasurePicker: Boolean,
    setShowMeasurePicker: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // Lista jednostek czasu
    val timeUnits = listOf("Minuta", "Godzina", "Dzień", "Miesiąc")

    Box(
        modifier = modifier
            .height(50.dp)
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
            text = selectedMeasureUnit,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }

    // Okno dialogowe wyboru jednostki czasu
    if (showMeasurePicker) {
        AlertDialog(
            onDismissRequest = { setShowMeasurePicker(false) },
            title = { Text("Wybierz jednostkę czasu") },
            text = {
                Column {
                    timeUnits.forEach { unit ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0x4DFFFFFF))
                                .clickable {
                                    onMeasureUnitSelected(unit)
                                    setShowMeasurePicker(false)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = unit,
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = if (unit == selectedMeasureUnit) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { setShowMeasurePicker(false) }) {
                    Text("Zamknij")
                }
            }
        )
    }

}
