package com.example.pracainynierska

import TaskManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pracainynierska.API.api_client.AugmentApi
import com.example.pracainynierska.API.api_client.TaskApi
import com.example.pracainynierska.context.PlayerContext
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.dictionary.ViewRoutes
import com.example.pracainynierska.manager.augment.AugmentManager
import com.example.pracainynierska.ui.theme.PracaInżynierskaTheme
import com.example.pracainynierska.ui_view_components.ProfileView
import com.example.pracainynierska.ui_view_components.view.AchievementsView
import com.example.pracainynierska.ui_view_components.view.AddCategoryView
import com.example.pracainynierska.ui_view_components.view.AddStatisticView
import com.example.pracainynierska.ui_view_components.view.AddTaskView
import com.example.pracainynierska.ui_view_components.view.CalendarsView
import com.example.pracainynierska.ui_view_components.view.ChangeForgotPasswordView
import com.example.pracainynierska.ui_view_components.view.EditTaskView
import com.example.pracainynierska.ui_view_components.view.ForgotPasswordView
import com.example.pracainynierska.ui_view_components.view.HomepageView
import com.example.pracainynierska.ui_view_components.view.LoginView
import com.example.pracainynierska.ui_view_components.view.ShopView
import com.example.pracainynierska.ui_view_components.view.CategoryView
import com.example.pracainynierska.ui_view_components.view.EditCategoryView
import com.example.pracainynierska.ui_view_components.view.EditStatisticView
import com.example.pracainynierska.view_model.AddCategoryViewModel
import com.example.pracainynierska.view_model.AddCategoryViewModelFactory
import com.example.pracainynierska.view_model.AddStatisticViewModel
import com.example.pracainynierska.view_model.AddStatisticViewModelFactory
import com.example.pracainynierska.view_model.HomepageViewModel
import com.example.pracainynierska.view_model.HomepageViewModelFactory
import com.example.pracainynierska.ui_view_components.view.RegisterView as RegisterView
import com.example.pracainynierska.view_model.LoginViewModel
import com.example.pracainynierska.view_model.LoginViewModelFactory
import com.example.pracainynierska.view_model.ProfileViewModel
import com.example.pracainynierska.view_model.ProfileViewModelFactory
import com.example.pracainynierska.view_model.RegistrationViewModel
import com.example.pracainynierska.view_model.RegistrationViewModelFactory
import com.example.pracainynierska.view_model.ShopViewModel
import com.example.pracainynierska.view_model.ShopViewModelFactory
import com.example.pracainynierska.view_model.CategoryViewModel
import com.example.pracainynierska.view_model.CategoryViewModelFactory
import com.example.pracainynierska.view_model.EditCategoryViewModel
import com.example.pracainynierska.view_model.EditCategoryViewModelFactory
import com.example.pracainynierska.view_model.EditStatisticViewModel
import com.example.pracainynierska.view_model.EditStatisticViewModelFactory
import com.example.pracainynierska.view_model.TaskViewModel
import com.example.pracainynierska.view_model.TaskViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var playerContext: PlayerContextInterface
    private lateinit var augmentManager: AugmentManager
    private lateinit var taskManager: TaskManager


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerContext = PlayerContext()
        augmentManager = AugmentManager(AugmentApi(playerContext))
        taskManager = TaskManager(TaskApi(playerContext))

        setContent {
            val context = LocalContext.current

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
                        factory = HomepageViewModelFactory(playerContext, context)
                    )
                    val taskViewModel : TaskViewModel = viewModel(
                        factory = TaskViewModelFactory(playerContext, taskManager)
                    )
                    val shopViewModel : ShopViewModel = viewModel(
                        factory = ShopViewModelFactory(playerContext, augmentManager)
                    )

                    val categoryViewModel : CategoryViewModel = viewModel(
                        factory = CategoryViewModelFactory(playerContext, context)
                    )
                    val addCategoryViewModel : AddCategoryViewModel = viewModel(
                        factory = AddCategoryViewModelFactory(playerContext)
                    )
                    val editCategoryViewModel : EditCategoryViewModel = viewModel(
                        factory = EditCategoryViewModelFactory(playerContext)
                    )
                    val addStatisticViewModel : AddStatisticViewModel = viewModel(
                        factory = AddStatisticViewModelFactory(playerContext)
                    )
                    val editStatisticViewModel : EditStatisticViewModel = viewModel(
                        factory = EditStatisticViewModelFactory(playerContext)
                    )
                    val profileViewModel : ProfileViewModel = viewModel(
                        factory = ProfileViewModelFactory(playerContext, context)
                    )
                    SetupNavGraph(
                        navController = navController,
                        loginViewModel = loginViewModel,
                        registrationViewModel = registrationViewModel,
                        homepageViewModel = homepageViewModel,
                        taskViewModel = taskViewModel,
                        shopViewModel = shopViewModel,
                        categoryViewModel = categoryViewModel,
                        addCategoryViewModel = addCategoryViewModel,
                        editCategoryViewModel = editCategoryViewModel,
                        addStatisticViewModel = addStatisticViewModel,
                        editStatisticViewModel = editStatisticViewModel,
                        profileViewModel = profileViewModel
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    registrationViewModel: RegistrationViewModel,
    homepageViewModel: HomepageViewModel,
    taskViewModel: TaskViewModel,
    shopViewModel: ShopViewModel,
    categoryViewModel: CategoryViewModel,
    addCategoryViewModel: AddCategoryViewModel,
    editCategoryViewModel: EditCategoryViewModel,
    addStatisticViewModel : AddStatisticViewModel,
    editStatisticViewModel: EditStatisticViewModel,
    profileViewModel: ProfileViewModel
) {
    NavHost(
        navController = navController,
        startDestination = ViewRoutes.LOGIN.viewName
    ) {
        composable(ViewRoutes.LOGIN.viewName) {
            LoginView(navController = navController, loginViewModel = loginViewModel)
        }
        composable(ViewRoutes.REGISTER.viewName) {
            RegisterView(navController = navController, registrationViewModel = registrationViewModel)
        }
        composable(ViewRoutes.HOMEPAGE.viewName) {
            HomepageView(navController = navController, homepageViewModel = homepageViewModel)
                .renderView()
        }
        composable(ViewRoutes.FORGOTPASSWORD.viewName) {
            ForgotPasswordView(navController = navController, loginViewModel = loginViewModel)
        }
        composable(ViewRoutes.CHANGEFORGOTPASSWORD.viewName) {
            ChangeForgotPasswordView(navController = navController, loginViewModel = loginViewModel)
        }
        composable(ViewRoutes.SHOP.viewName) {
            ShopView(navController = navController, shopViewModel = shopViewModel)
                .renderView()
        }
        composable(ViewRoutes.PROFILE.viewName) {
            ProfileView(navController = navController, profileViewModel = profileViewModel)
                .renderView()
        }
        composable(ViewRoutes.ADDTASK.viewName) {
            AddTaskView(navController = navController, taskViewModel = taskViewModel)
                .renderView()
        }
        composable(ViewRoutes.CALENDAR.viewName) {
            CalendarsView(navController = navController, taskViewModel = taskViewModel)
                .renderView()
        }
        composable(ViewRoutes.CATEGORIES.viewName) {
            CategoryView(navController = navController, viewModel = categoryViewModel)
                .renderView()
        }
        composable(ViewRoutes.ADDCATEGORY.viewName) {
            AddCategoryView(navController = navController, viewModel = addCategoryViewModel)
                .renderView()
        }
        composable(ViewRoutes.ADDSTATISTIC.viewName) {
            AddStatisticView(navController = navController, viewModel = addStatisticViewModel)
                .renderView()
        }
        composable(ViewRoutes.EDITSTATISTIC.viewName) {
            EditStatisticView(navController = navController, viewModel = editStatisticViewModel)
                .renderView()
        }
        composable("{${ViewRoutes.EDITTASK.viewName}}/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val taskToEdit = taskViewModel.getTaskById(taskId)
            if (taskToEdit != null) {
                EditTaskView(
                    taskToEdit = taskToEdit,
                    navController = navController,
                    taskViewModel = taskViewModel
                )
                    .renderView()
            }
        }
        composable("{${ViewRoutes.EDITCATEGORY.viewName}}/{categoryId}") { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            val categoryToEdit = categoryViewModel.getCategoryById(categoryId)
            if (categoryToEdit != null) {
                EditCategoryView(
                    categoryToEdit = categoryToEdit,
                    viewModel = editCategoryViewModel,
                    navController = navController
                )
                    .renderView()
            }
        }
        composable(ViewRoutes.ACHIEVEMENTS.viewName) {
            AchievementsView(navController = navController, loginViewModel = loginViewModel)
                .renderView()
        }

    }
}

