package com.example.pracainynierska.ui_view_components.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pracainynierska.R

@Composable
fun CustomCreateStatisticButton(
    onCreateClick: () -> Unit,
    isStatValid: Boolean,
    showAlert: MutableState<Boolean>,
    showSuccessAlert: MutableState<Boolean>,
    alertMessage: String,
    successMessage: String
) {
    Box(
        modifier = Modifier
            .height(75.dp)
            .padding(vertical = 4.dp)
            .background(
                color = Color(0x19FFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                if (isStatValid) {
                    onCreateClick()
                    showSuccessAlert.value = true
                } else {
                    showAlert.value = true
                }
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.plus_square),
                contentDescription = stringResource(R.string.icon_statistic_description),
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )

            Text(
                text = stringResource(R.string.create),
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }

    if (showAlert.value) {
        AlertDialog(
            onDismissRequest = { showAlert.value = false },
            title = { Text(stringResource(R.string.validation_error)) },
            text = { Text(alertMessage) },
            confirmButton = {
                TextButton(
                    onClick = { showAlert.value = false }
                ) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }

    if (showSuccessAlert.value) {
        AlertDialog(
            onDismissRequest = { showSuccessAlert.value = false },
            title = { Text("Sukces!") },
            text = { Text(successMessage) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessAlert.value = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}
