package com.example.pracainynierska.ui_view_components.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.example.pracainynierska.R

@Composable
fun CustomAlertDialog(
    showAlert: MutableState<Boolean>,
    alertTitleId: Int,
    alertMessageId: Int,
    toForward: Boolean = true,
    onConfirmClick: () -> Unit,
    dismissButton: Boolean = false,
    onDismissRequest: () -> Unit = { }
) {
    if (showAlert.value) {
        AlertDialog(
            onDismissRequest = { showAlert.value = false },
            title = { Text(stringResource(alertTitleId)) },
            text = { Text(stringResource(alertMessageId)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showAlert.value = false
                        if (toForward) {
                            onConfirmClick()
                        }
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = if (dismissButton) {
                {
                    TextButton(
                        onClick = {
                            showAlert.value = false
                            onDismissRequest()
                        }
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            } else null
        )
    }
}
