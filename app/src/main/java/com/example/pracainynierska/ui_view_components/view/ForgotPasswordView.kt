package com.example.pracainynierska.ui_view_components.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.ViewRoutes
import com.example.pracainynierska.view_model.LoginViewModel

@Composable
fun ForgotPasswordView(navController: NavController, loginViewModel: LoginViewModel) {

    var showDialog by remember { mutableStateOf(false) }
    var forgotPasswordMessage by remember { mutableStateOf("") }
    var isDialogError by remember { mutableStateOf(false) }

    var isResetCodeSent by remember { mutableStateOf(false) }

    val codeInputs = remember { mutableStateListOf("", "", "", "", "", "") }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Column (
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

            if (isResetCodeSent) {
                Column(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(stringResource(R.string.enter_reset_code), color = Color.Black, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                        repeat(6) { index ->
                            OutlinedTextField(
                                value = codeInputs[index],
                                onValueChange = {
                                    if (it.length <= 1) {
                                        codeInputs[index] = it
                                        if (it.isNotEmpty() && index < 5) {
                                            focusManager.moveFocus(FocusDirection.Next)
                                        }
                                    }
                                    Log.d("CodeInputs", codeInputs.joinToString(""))
                                },
                                modifier = Modifier.width(50.dp),
                                textStyle = TextStyle(fontSize = 24.sp, color = Color.Black, textAlign = TextAlign.Center),
                                singleLine = true,
                                shape = RoundedCornerShape(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Gray,
                                    errorTextColor = Color.Red
                                ),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val enteredCode = codeInputs.joinToString("")
                            if (enteredCode == "111111") {
                                navController.navigate(ViewRoutes.CHANGEFORGOTPASSWORD.viewName)
                            } else {
                                forgotPasswordMessage =R.string.invalid_reset_code.toString()
                                isDialogError = true
                                showDialog = true
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .height(OutlinedTextFieldDefaults.MinHeight)
                            .fillMaxWidth()
                    ) {
                        Text(text = stringResource(R.string.confirm_code))
                    }

                }
            } else {
                OutlinedTextField(
                    value = loginViewModel.email,
                    onValueChange = {loginViewModel.onEmailChange(it)},
                    label = { Text(text = stringResource(R.string.email))},
                    isError = loginViewModel.emailErrorMessageId != 0,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray,
                        errorTextColor = Color.Red
                    ),
                    shape = RoundedCornerShape(16.dp),
                    trailingIcon = {
                        if(loginViewModel.emailErrorMessageId != 0){
                            Icon(Icons.Default.Warning, contentDescription = stringResource(R.string.icon_error_description), tint = Color.Red)
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.email),
                                contentDescription = stringResource(R.string.icon_email_description),
                                modifier = Modifier
                                    .size(24.dp)
                                    .alpha(0.5f)
                            )
                        }
                    },
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.clearFocus()
                        }
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )

                if (loginViewModel.emailErrorMessageId != 0){
                    Text(text = stringResource(loginViewModel.emailErrorMessageId), color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        loginViewModel.forgotPassword(
                            loginViewModel.email,
                            onSuccess = {
                                forgotPasswordMessage = R.string.information_email_reset_code.toString()
                                isDialogError = false
                                showDialog = true
                            },
                            onError = {
                                forgotPasswordMessage = it
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
                    Text(text = stringResource(R.string.reset_password))
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    title = { Text(text = if (isDialogError) stringResource(R.string.error) else stringResource(R.string.success)) },
                    text = { Text(text = forgotPasswordMessage) },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            isResetCodeSent = true
                        }) {
                            Text(stringResource(R.string.ok))
                        }
                    }
                )
            }
        }
    }
}