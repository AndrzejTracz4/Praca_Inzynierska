package com.example.pracainynierska

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pracainynierska.database.UserDatabase
import com.example.pracainynierska.repository.UserRepository
import com.example.pracainynierska.ui.theme.PracaInżynierskaTheme
import com.example.pracainynierska.view.ForgotPasswordView
import com.example.pracainynierska.view.HomepageView
import com.example.pracainynierska.view.LoginView
import com.example.pracainynierska.view.RegisterView

class MainActivity : ComponentActivity() {

    private lateinit var userRepository: UserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userDao = UserDatabase.getDatabase(applicationContext).dao
        userRepository = UserRepository(userDao)

        setContent {
            PracaInżynierskaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SetupNavGraph(navController = navController, userRepository = userRepository)
                }
            }
        }
    }
}


@Composable
fun SetupNavGraph(navController: NavHostController, userRepository: UserRepository) {
    NavHost(
        navController = navController,
        startDestination = "LoginView"
    ) {
        composable("LoginView") {
            LoginView(navController = navController, userRepository = userRepository)
        }
        composable("RegisterView") {
            RegisterView(navController = navController, userRepository = userRepository)
        }
        composable("HomepageView/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            HomepageView(navController, userRepository, username)
        }
        composable("ForgotPasswordView") {
            ForgotPasswordView(navController = navController, userRepository = userRepository)
        }

    }
}

