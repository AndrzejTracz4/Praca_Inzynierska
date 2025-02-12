package com.example.pracainynierska.view_model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pracainynierska.API.handler.authorization.AuthorizationHandlerInterface
import com.example.pracainynierska.R
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.achievement.AchievementManager
import com.example.pracainynierska.manager.achievement.AchievementManagerInterface
import com.example.pracainynierska.manager.task.TaskManagerInterface
import com.example.pracainynierska.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LoginViewModel(
    pc: PlayerContextInterface,
    private val playerAuthorizationHandler: AuthorizationHandlerInterface,
    private val taskManager: TaskManagerInterface,
) : AbstractViewModel(pc) {

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var loginSuccess by mutableStateOf(false)
        private set

    var passwordErrorMessageId by mutableIntStateOf(0)
        private set

    var emailErrorMessageId by mutableIntStateOf(0)
        private set

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        passwordErrorMessageId = validatePassword(newPassword)
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
        emailErrorMessageId = validateEmail(newEmail)
    }

    private fun validatePassword(password: String): Int {
        return if (password.isBlank()) R.string.validation_password_cannot_be_empty else 0
    }

    private fun validateEmail(email: String): Int {
        return if (email.isBlank()) R.string.validation_email_cannot_be_empty else 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun login(onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val player = playerAuthorizationHandler.authorize(email, password)
            if (player != null) {
                initializeUserData()
                Log.d("LoginViewModel", "Player: $player")
                loginSuccess = true
                onLoginResult(true)
            } else {
                Log.d("LoginViewModel", "Player is null")
                emailErrorMessageId = R.string.invalid_username_or_password
                passwordErrorMessageId = R.string.invalid_username_or_password
                onLoginResult(false)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initializeUserData(){
        withContext(Dispatchers.IO) {
            val today = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formattedDate = today.format(formatter)
            Log.d("initializeUserData", "Formatted date: $formattedDate")
            taskManager.getByDate(formattedDate)
        }
    }
}