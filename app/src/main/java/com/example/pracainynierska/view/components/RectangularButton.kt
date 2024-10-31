package com.example.pracainynierska.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RectangularButton(text: String, isSelected: Boolean, onClick: () -> Unit, iconResId: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp) // Set a standard height for the buttons
            .padding(4.dp)
            .background(
                color = if (isSelected) Color(0x4DFFFFFF) else Color(0x19FFFFFF),
                shape = RoundedCornerShape(12.dp) // Rounded corners
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center // Center the content
        ) {
            // Image
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(32.dp), // Size of the icon
                tint = Color.White // Tint the icon with white color
            )

            Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text

            // Text
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
