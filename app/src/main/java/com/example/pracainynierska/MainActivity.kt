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
import com.example.pracainynierska.API.api_client.PlayerApi
import com.example.pracainynierska.API.handler.registration.RegistrationHandler
import com.example.pracainynierska.context.PlayerContext
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.ui.theme.PracaInżynierskaTheme
import com.example.pracainynierska.ui_view_components.view.AddCategoryView
import com.example.pracainynierska.ui_view_components.view.AddTaskView
import com.example.pracainynierska.ui_view_components.view.CalendarsView
import com.example.pracainynierska.ui_view_components.view.ChangeForgotPasswordView
import com.example.pracainynierska.ui_view_components.view.EditTaskView
import com.example.pracainynierska.ui_view_components.view.ForgotPasswordView
import com.example.pracainynierska.ui_view_components.view.HomepageView
import com.example.pracainynierska.ui_view_components.view.LoginView
import com.example.pracainynierska.ui_view_components.view.ShopView
import com.example.pracainynierska.ui_view_components.view.StatisticView
import com.example.pracainynierska.view_model.HomepageViewModel
import com.example.pracainynierska.view_model.HomepageViewModelFactory
import com.example.pracainynierska.ui_view_components.view.RegisterView as RegisterView
import com.example.pracainynierska.view_model.LoginViewModel
import com.example.pracainynierska.view_model.LoginViewModelFactory
import com.example.pracainynierska.view_model.RegistrationViewModel
import com.example.pracainynierska.view_model.RegistrationViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var playerContext: PlayerContextInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerContext = PlayerContext()

        setContent {
            PracaInżynierskaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val loginViewModel: LoginViewModel = viewModel(
                        factory = LoginViewModelFactory(playerContext)
                    )
                    val registrationViewModel : RegistrationViewModel = viewModel(
                        factory = RegistrationViewModelFactory(playerContext)
                    )
                    val homepageViewModel : HomepageViewModel = viewModel(
                        factory = HomepageViewModelFactory(playerContext)
                    )
                    SetupNavGraph(
                        navController = navController,
                        loginViewModel = loginViewModel,
                        registrationViewModel = registrationViewModel,
                        homepageViewModel = homepageViewModel
                    )
                }
            }
        }
    }
}


@Composable
fun SetupNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    registrationViewModel: RegistrationViewModel,
    homepageViewModel: HomepageViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "LoginView"
    ) {
        composable("LoginView") {
            LoginView(navController = navController, loginViewModel = loginViewModel)
        }
        composable("RegisterView") {
            RegisterView(navController = navController, registrationViewModel = registrationViewModel)
        }
        composable("HomepageView") {
            HomepageView(navController = navController, homepageViewModel = homepageViewModel)
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
        composable("StatisticView") {
            StatisticView(navController = navController, loginViewModel = loginViewModel)
        }
        composable("AddCategoryView") {
            AddCategoryView(navController = navController, loginViewModel = loginViewModel)
        }
        composable("EditTaskView/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val taskToEdit = loginViewModel.getTaskById(taskId)
            if (taskToEdit != null) {
                EditTaskView(
                    taskToEdit = taskToEdit,
                    navController = navController,
                    loginViewModel = loginViewModel
                )
            }
        }

    }
}

