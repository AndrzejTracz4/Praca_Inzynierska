package com.example.pracainynierska.view

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
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.repository.UserRepository
import com.example.pracainynierska.viewmodel.LoginViewModel
import com.example.pracainynierska.viewmodel.LoginViewModelFactory

@Composable
fun ChangeForgotPasswordView(navController: NavController, loginViewModel: LoginViewModel) {

    var showDialog by remember { mutableStateOf(false) }
    var passwordMessage by remember { mutableStateOf("") }
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
                value = loginViewModel.newPassword,
                onValueChange = { loginViewModel.onNewPasswordChange(it) },
                label = { Text(text = "Nowe hasło") },
                isError = loginViewModel.newPasswordErrorMessage != null,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp)
            )

            if (loginViewModel.newPasswordErrorMessage != null) {
                Text(text = loginViewModel.newPasswordErrorMessage!!, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = loginViewModel.confirmNewPassword,
                onValueChange = { loginViewModel.onConfirmNewPasswordChange(it) },
                label = { Text(text = "Potwierdź nowe hasło") },
                isError = loginViewModel.confirmNewPasswordErrorMessage != null,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp)
            )

            if (loginViewModel.confirmNewPasswordErrorMessage != null) {
                Text(text = loginViewModel.confirmNewPasswordErrorMessage!!, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    loginViewModel.changePasswordByEmail(
                        email = loginViewModel.email,
                        newPassword = loginViewModel.newPassword,
                        onSuccess = {
                            passwordMessage = "Hasło zostało zmienione pomyślnie."
                            isDialogError = false
                            showDialog = true
                        },
                        onError = {
                            passwordMessage = it
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
                Text(text = "Zmień hasło")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = if (isDialogError) "Błąd" else "Sukces") },
            text = { Text(text = passwordMessage) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.navigate("LoginView"){
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }) {
                    Text("OK")
                }
            }
        )
    }
}
