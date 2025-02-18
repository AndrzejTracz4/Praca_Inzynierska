package com.example.pracainynierska.view_model

import android.app.Application
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
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.auth0.android.jwt.JWT
import com.example.pracainynierska.API.Exception.RequestValidationException
import com.example.pracainynierska.API.handler.authorization.AuthorizationHandlerInterface
import com.example.pracainynierska.R
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.manager.daily_challenge.DailyChallengeManagerInterface
import com.example.pracainynierska.manager.task.TaskManagerInterface
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LoginViewModel(
    pc: PlayerContextInterface,
    private val playerAuthorizationHandler: AuthorizationHandlerInterface,
    private val taskManager: TaskManagerInterface,
    private val dailyChallengeManager: DailyChallengeManagerInterface,
    application: Application
) : AbstractViewModel(pc) {

    private val _isUserLoggedIn = MutableLiveData(false)
    val isUserLoggedIn: LiveData<Boolean> get() = _isUserLoggedIn

    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        application,
        "secure_prefs",
        MasterKey.Builder(application)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

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

    fun isTokenValid(): Boolean {
        val token = getToken() ?: return false
        return try {
            val jwt = JWT(token)
            !jwt.isExpired(0)
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Token validation failed", e)
            false
        }
    }

    private fun saveToken(token: String) {
        encryptedSharedPreferences.edit().putString("jwt_token", token).apply()
    }

    fun getToken(): String? {
        val token = encryptedSharedPreferences.getString("jwt_token", null)
        Log.d("LoginViewModel", "Retrieved token: $token")
        return token
    }

    fun removeToken() {
        encryptedSharedPreferences.edit().remove("jwt_token").apply()
    }

    fun checkIfTokenExists(): Boolean {
        val token = getToken()
        if (token == null || !isTokenValid()) {
            removeToken()
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun tryLoginFromToken() {
        if (checkIfTokenExists()) {
            val token = getToken()
            token?.let {
                authorizePlayerFromToken { success ->
                    _isUserLoggedIn.postValue(success)
                }
            }
        } else {
            _isUserLoggedIn.postValue(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun authorizePlayerFromToken(onLoginResult: (Boolean) -> Unit) {
        val token = getToken()
        if (token != null) {
            viewModelScope.launch {
                try {
                    val player = playerAuthorizationHandler.authorizeFromToken(token)
                    val success = player != null
                    _isUserLoggedIn.postValue(success)
                    onLoginResult(success)
                    if (success) {
                        initializeUserData()
                        Log.d("LoginViewModel", "User logged in from token: $player")
                    }
                } catch (e: Exception) {
                    Log.e("LoginViewModel", "Failed to log in from token", e)
                    _isUserLoggedIn.postValue(false)
                    onLoginResult(false)
                }
            }
        } else {
            _isUserLoggedIn.postValue(false)
            onLoginResult(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun login(onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val player = playerAuthorizationHandler.authorize(email, password)
            if (player != null) {
                playerContext.getToken()?.let { saveToken(it) }
                initializeUserData()
                _isUserLoggedIn.postValue(true)
                onLoginResult(true)
            } else {
                _isUserLoggedIn.postValue(false)
                onLoginResult(false)
            }
        }
    }

    fun logout() {
        removeToken()
        _isUserLoggedIn.postValue(false)
        Log.d("LoginViewModel", "User logged out, token removed")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initializeUserData() {
        viewModelScope.launch {
            try {
                val today = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formattedDate = today.format(formatter)
                Log.d("initializeUserData", "Formatted date: $formattedDate")
                taskManager.getByDate(formattedDate)
                val dailyTask = taskManager.getDailyChallenge()
                if (dailyTask != null) {
                    dailyChallengeManager.checkStatus(dailyTask)
                }
                dailyChallengeManager.load()
            } catch (e: RequestValidationException) {
                Log.e("LoginViewModel", "Validation exception")
            } catch (e: Exception) {
                Log.e("LoginViewModel - failed", e.message.toString())
            }
        }
    }
}