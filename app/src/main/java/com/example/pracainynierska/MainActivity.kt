package com.example.pracainynierska

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pracainynierska.database.UserDatabase
import com.example.pracainynierska.repository.UserRepository
import com.example.pracainynierska.ui.theme.PracaInżynierskaTheme
import com.example.pracainynierska.view.AddTaskView
import com.example.pracainynierska.view.CalendarsView
import com.example.pracainynierska.view.ChangeForgotPasswordView
import com.example.pracainynierska.view.ForgotPasswordView
import com.example.pracainynierska.view.HomepageView
import com.example.pracainynierska.view.LoginView
import com.example.pracainynierska.view.RegisterView
import com.example.pracainynierska.view.ShopView
import com.example.pracainynierska.viewmodel.LoginViewModel
import com.example.pracainynierska.viewmodel.LoginViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var userRepository: UserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userDao = UserDatabase.getDatabase(applicationContext).dao
        userRepository = UserRepository(userDao)


        setContent {
            PracaInżynierskaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val loginViewModel: LoginViewModel = viewModel(
                        factory = LoginViewModelFactory(userRepository)
                    )
                    SetupNavGraph(navController = navController, loginViewModel = loginViewModel)
                }
            }
        }
    }
}


@Composable
fun SetupNavGraph(navController: NavHostController, loginViewModel: LoginViewModel) {
    NavHost(
        navController = navController,
        startDestination = "LoginView"
    ) {
        composable("LoginView") {
            LoginView(navController = navController, loginViewModel = loginViewModel)
        }
        composable("RegisterView") {
            RegisterView(navController = navController, loginViewModel = loginViewModel)
        }
        composable("HomepageView") {
            HomepageView(navController = navController, loginViewModel = loginViewModel)
        }
        composable("ForgotPasswordView") {
            ForgotPasswordView(navController = navController, loginViewModel = loginViewModel)
        }
        composable("ChangeForgotPasswordView") {
            ChangeForgotPasswordView(navController = navController, loginViewModel = loginViewModel)
        }
        composable("ShopView") {
            ShopView(navController = navController, loginViewModel = loginViewModel)
        }
        composable("AddTaskView") {
            AddTaskView(navController = navController, loginViewModel = loginViewModel)
        }
        composable("CalendarsView") {
            CalendarsView(navController = navController, loginViewModel = loginViewModel)
        }
    }
}

