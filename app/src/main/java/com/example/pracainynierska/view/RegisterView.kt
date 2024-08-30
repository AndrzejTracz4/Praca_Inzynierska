package com.example.pracainynierska.view
import com.example.pracainynierska.viewmodel.LoginViewModelFactory
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.repository.UserRepository
import com.example.pracainynierska.viewmodel.LoginViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterView(navController: NavController, userRepository: UserRepository){

    val focusManager = LocalFocusManager.current

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(userRepository)
    )

    var showDialog by remember { mutableStateOf(false) }
    var registrationMessage by remember { mutableStateOf("") }

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
                contentDescription = "Logo Questa",
                modifier = Modifier.size(450.dp, 150.dp)
            )

            Text(
                text = "Questa",
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "\"Make it happen.\"",
                color = Color.Black,
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = loginViewModel.username,
                onValueChange = {
                    loginViewModel.onUsernameChange(it)},
                label = { Text(text = "Username")},
                isError = loginViewModel.usernameErrorMessage != null,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    if (loginViewModel.usernameErrorMessage != null){
                        Icon(Icons.Default.Warning, contentDescription = "Error", tint = Color.Red)
                    }
                    else
                    {
                        Image(
                            painter = painterResource(id = R.drawable.username),
                            contentDescription = "Username",
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

            if (loginViewModel.usernameErrorMessage != null){
                Text(text = loginViewModel.usernameErrorMessage!!, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = loginViewModel.email,
                onValueChange = {
                    loginViewModel.onEmailChange(it)},
                label = { Text(text = "Email")},
                isError = loginViewModel.emailErrorMessage != null,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    if(loginViewModel.emailErrorMessage != null){
                        Icon(Icons.Default.Warning, contentDescription = "Error", tint = Color.Red)
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.email),
                            contentDescription = "Email",
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

            if (loginViewModel.emailErrorMessage != null){
                Text(text = loginViewModel.emailErrorMessage!!, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = loginViewModel.password,
                onValueChange = {loginViewModel.onPasswordChange(it)},
                label = { Text(text = "Password")},
                isError = loginViewModel.passwordErrorMessage != null,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    if(loginViewModel.passwordErrorMessage != null){
                        Icon(Icons.Default.Warning, contentDescription = "Error", tint = Color.Red)
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.password),
                            contentDescription = "Password",
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

            if (loginViewModel.passwordErrorMessage != null){
                Text(text = loginViewModel.passwordErrorMessage!!, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = loginViewModel.confirmPassword,
                onValueChange = {loginViewModel.onConfirmPasswordChange(it)},
                label = { Text(text = "Confirm password")},
                isError = loginViewModel.confirmPasswordErrorMessage != null,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    if(loginViewModel.confirmPasswordErrorMessage != null){
                        Icon(Icons.Default.Warning, contentDescription = "Error", tint = Color.Red)
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.password),
                            contentDescription = "Password",
                            modifier = Modifier
                                .size(24.dp)
                                .alpha(0.5f)
                        )
                    }
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.moveFocus(FocusDirection.Enter)
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            if (loginViewModel.confirmPasswordErrorMessage != null){
                Text(text = loginViewModel.confirmPasswordErrorMessage!!, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    loginViewModel.registerUser(
                        onSuccess = {
                            registrationMessage = "Registration successful"
                            showDialog = true
                        },
                        onError = {
                            registrationMessage = it
                            showDialog = true
                        }
                    )
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .height(OutlinedTextFieldDefaults.MinHeight)
                    .width(OutlinedTextFieldDefaults.MinWidth)
            ) {
                Text(text = "Register", fontSize = 16.sp)
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "Registration Status") },
                    text = { Text(text = registrationMessage) },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            if (registrationMessage == "Registration successful") {
                                navController.navigate("HomepageView")
                            }
                        }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}