package com.example.pracainynierska.ui_view_components.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.ViewRoutes
import com.example.pracainynierska.view_model.ChangePasswordViewModel

@Composable
fun ChangePasswordView(navController: NavController, changePasswordViewModel: ChangePasswordViewModel) {

    var showDialog by remember { mutableStateOf(false) }
    var passwordMessageId by remember { mutableIntStateOf(0) }
    var isDialogError by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,

            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.questa_logo),
                contentDescription = stringResource(R.string.icon_logo_questa_description),
                modifier = Modifier.size(450.dp, 150.dp)
            )

            Text(
                text = stringResource(R.string.app_name),
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.app_quote),
                color = Color.Black,
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = changePasswordViewModel.password,
                onValueChange = { changePasswordViewModel.onPasswordChange(it) },
                label = { Text(text = stringResource(R.string.new_password)) },
                isError = changePasswordViewModel.passwordErrorMessageId != 0,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp)
            )

            if (changePasswordViewModel.passwordErrorMessageId != 0) {
                Text(text = stringResource(changePasswordViewModel.passwordErrorMessageId), color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = changePasswordViewModel.confirmPassword,
                onValueChange = { changePasswordViewModel.onConfirmPasswordChange(it) },
                label = { Text(text = stringResource(R.string.reset_password)) },
                isError = changePasswordViewModel.confirmPasswordErrorMessageId != 0,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp)
            )

            if (changePasswordViewModel.confirmPasswordErrorMessageId != 0) {
                Text(text = stringResource(changePasswordViewModel.confirmPasswordErrorMessageId), color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    changePasswordViewModel.changePassword(
                        onSuccess = {
                            passwordMessageId = R.string.success_password_change
                            isDialogError = false
                            showDialog = true
                        },
                        onError = {
                            passwordMessageId = R.string.error_password_change
                            isDialogError = true
                            showDialog = true
                        }
                    )
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .height(OutlinedTextFieldDefaults.MinHeight)
                    .width(OutlinedTextFieldDefaults.MinWidth)
            ) {
                Text(text = stringResource(R.string.change_password))
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = if (isDialogError) stringResource(R.string.error) else stringResource(R.string.success)) },
            text = { Text(stringResource(passwordMessageId)) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    if (!isDialogError) {
                        navController.navigate(ViewRoutes.LOGIN.viewName){
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    }
                }) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }
}
