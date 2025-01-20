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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.ViewRoutes
import com.example.pracainynierska.view_model.RegistrationViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterView(navController: NavController, registrationViewModel: RegistrationViewModel){

    val focusManager = LocalFocusManager.current

    var showDialog by remember { mutableStateOf(false) }
    var registrationMessageId by remember { mutableIntStateOf(0) }

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

            OutlinedTextField(
                value = registrationViewModel.username,
                onValueChange = {
                    registrationViewModel.onUsernameChange(it)},
                label = { Text(text = stringResource(R.string.username))},
                isError = registrationViewModel.getUserNameErrorMessage() != 0,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    if (registrationViewModel.getUserNameErrorMessage() != 0){
                        Icon(Icons.Default.Warning, contentDescription = stringResource(R.string.icon_error_description), tint = Color.Red)
                    }
                    else
                    {
                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = stringResource(R.string.icon_username_description),
                            modifier = Modifier
                                .size(24.dp)
                                .alpha(0.5f)
                        )
                    }
                },
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )

            if (registrationViewModel.getUserNameErrorMessage() != 0){
                Text(text = stringResource(registrationViewModel.getUserNameErrorMessage()), color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = registrationViewModel.email,
                onValueChange = {
                    registrationViewModel.onEmailChange(it)},
                label = { Text(text = stringResource(R.string.email))},
                isError = registrationViewModel.getEmailErrorMessage() != 0,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    if(registrationViewModel.getEmailErrorMessage() != 0){
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
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )

            if (registrationViewModel.getEmailErrorMessage() != 0){
                Text(text = stringResource(registrationViewModel.getEmailErrorMessage()), color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = registrationViewModel.password,
                onValueChange = {registrationViewModel.onPasswordChange(it)},
                label = { Text(text = stringResource(R.string.password))},
                isError = registrationViewModel.getPasswordErrorMessage() != 0,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    if(registrationViewModel.getPasswordErrorMessage() != 0){
                        Icon(Icons.Default.Warning, contentDescription = stringResource(R.string.icon_error_description), tint = Color.Red)
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.password),
                            contentDescription = stringResource(R.string.icon_password_description),
                            modifier = Modifier
                                .size(24.dp)
                                .alpha(0.5f)
                        )
                    }
                },
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )

            if (registrationViewModel.getPasswordErrorMessage() != 0){
                Text(text = stringResource(registrationViewModel.getPasswordErrorMessage()), color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = registrationViewModel.confirmPassword,
                onValueChange = {registrationViewModel.onConfirmPasswordChange(it)},
                label = { Text(text = stringResource(R.string.repeat_password))},
                isError = registrationViewModel.getConfirmPasswordErrorMessage() != 0,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    if(registrationViewModel.getConfirmPasswordErrorMessage() != 0){
                        Icon(Icons.Default.Warning, contentDescription = stringResource(R.string.icon_error_description), tint = Color.Red)
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.password),
                            contentDescription = stringResource(R.string.icon_repeat_password_description),
                            modifier = Modifier
                                .size(24.dp)
                                .alpha(0.5f)
                        )
                    }
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            if (registrationViewModel.getConfirmPasswordErrorMessage() != 0){
                Text(text = stringResource(registrationViewModel.getConfirmPasswordErrorMessage()), color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    registrationViewModel.registerUser(
                        onSuccess = {
                            registrationMessageId = R.string.success_registration_user
                            showDialog = true
                        },
                        onError = {
                            registrationMessageId = it
                            showDialog = true
                        }
                    )
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .height(OutlinedTextFieldDefaults.MinHeight)
                    .width(OutlinedTextFieldDefaults.MinWidth)
            ) {
                Text(text = stringResource(R.string.registration), fontSize = 16.sp)
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = stringResource(R.string.registration_status)) },
                    text = { Text(text = stringResource(registrationMessageId)) },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            if (registrationMessageId == R.string.success_registration_user) {
                                navController.navigate(ViewRoutes.LOGIN.viewName)
                            }
                        }) {
                            Text(stringResource(R.string.ok))
                        }
                    }
                )
            }
        }
    }
}