package com.example.pracainynierska.ui_view_components.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTextField(
    string: String,
    onStringChange: (String) -> Unit,
    singleLine: Boolean,
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = string,
        onValueChange = {
            onStringChange(it)
        },
        modifier =
            Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp, max = Int.MAX_VALUE.dp)
                .padding(vertical = 0.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (string.isNotBlank()) {
                                focusManager.clearFocus()
                            }
                        },
                    )
                },
        colors =
            TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
            ),
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else Int.MAX_VALUE,
        textStyle =
            LocalTextStyle.current.copy(
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = if (singleLine) TextAlign.Center else TextAlign.Start,
            ),
        keyboardActions =
            KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                },
            ),
        keyboardOptions =
            KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
            ),
    )
}
