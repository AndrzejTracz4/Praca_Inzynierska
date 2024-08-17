package com.example.pracainynierska
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.material3.Button
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun LoginScreen(){
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                value = username.value,
                onValueChange = {username.value = it},
                label = { Text(text = "Username")},
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.username),
                        contentDescription = "Username",
                        modifier = Modifier
                            .size(24.dp)
                            .alpha(0.5f)
                        )
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
            
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = {password.value = it},
                label = { Text(text = "Password")},
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorTextColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.password),
                        contentDescription = "Password",
                        modifier = Modifier
                            .size(24.dp)
                            .alpha(0.5f)
                        )
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

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .height(OutlinedTextFieldDefaults.MinHeight)
                    .width(OutlinedTextFieldDefaults.MinWidth)
            ) {
                Text(text = "Login")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .height(OutlinedTextFieldDefaults.MinHeight)
                    .width(OutlinedTextFieldDefaults.MinWidth)
            ) {
                Text(text = "Register")
            }

            Spacer(modifier = Modifier.height(120.dp))

            Text(
                text = "Forgot password",
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    //TODO
                },
                textDecoration = TextDecoration.Underline
                )
        }
    }
}