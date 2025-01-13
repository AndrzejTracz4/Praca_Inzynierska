package com.example.pracainynierska.ui_view_components.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomDrawerItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(vertical = 4.dp)
                .background(
                    color = if (isSelected) Color(0x4DFFFFFF) else Color(0x19FFFFFF),
                    shape = RoundedCornerShape(12.dp),
                ).clickable(onClick = onClick)
                .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Ikona
            icon() // Wywołanie przekazanej funkcji ikony

            Spacer(modifier = Modifier.width(8.dp)) // Odstęp między ikoną a tekstem

            // Tekst
            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
            )
        }
    }
}
