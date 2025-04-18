package com.example.pracainynierska.ui_view_components.view
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.ViewRoutes
import com.example.pracainynierska.view_model.LoginViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginView(navController: NavController, loginViewModel: LoginViewModel){

    val focusManager = LocalFocusManager.current

    var loginResult by remember { mutableStateOf(false) }

    if (loginResult) {
        LaunchedEffect(loginResult) {
            navController.navigate(ViewRoutes.HOMEPAGE.viewName) {
                popUpTo(ViewRoutes.LOGIN.viewName) { inclusive = true }
            }
        }
    }

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
                value = loginViewModel.email,
                onValueChange = {
                    loginViewModel.onEmailChange(it)},
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
                    if (loginViewModel.emailErrorMessageId != 0){
                        Icon(Icons.Default.Warning, contentDescription = stringResource(R.string.icon_error_description), tint = Color.Red)
                    }
                    else
                    {
                    Image(
                        painter = painterResource(id = R.drawable.user),
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
            
            if (loginViewModel.emailErrorMessageId != 0){
                Text(text = stringResource(loginViewModel.emailErrorMessageId), color = Color.Red, fontSize = 12.sp)
            }
            
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = loginViewModel.password,
                onValueChange = {loginViewModel.onPasswordChange(it)},
                label = { Text(text = stringResource(R.string.password))},
                isError = loginViewModel.passwordErrorMessageId != 0,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    if(loginViewModel.passwordErrorMessageId != 0){
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
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            if (loginViewModel.passwordErrorMessageId != 0){
                Text(text = stringResource(loginViewModel.passwordErrorMessageId), color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    loginViewModel.login { success ->
                        if (success) {
                            loginResult = true
                        } else {
                            loginResult = false
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .height(OutlinedTextFieldDefaults.MinHeight)
                    .width(OutlinedTextFieldDefaults.MinWidth)
            ) {
                Text(text = stringResource(R.string.login))
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = {
                      navController.navigate(ViewRoutes.REGISTER.viewName)
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .height(OutlinedTextFieldDefaults.MinHeight)
                    .width(OutlinedTextFieldDefaults.MinWidth)
            ) {
                Text(text = stringResource(R.string.register))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.forgot_password),
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.clickable {

                    navController.navigate(ViewRoutes.FORGOTPASSWORD.viewName)
                },
                textDecoration = TextDecoration.Underline
                )
        }
    }
}