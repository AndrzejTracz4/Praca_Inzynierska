package com.example.pracainynierska.ui_view_components.view

import BottomMenu
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.pracainynierska.ui.components.ModalDrawer
import com.example.pracainynierska.ui_view_components.components.TopMenu
import com.example.pracainynierska.view_model.AbstractViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class AbstractView(
    protected val viewModel: AbstractViewModel,
    protected val navController: NavController,
) {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    open fun renderView() {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val player = viewModel.getPlayer()

        ModalDrawer(navController = navController, drawerState = drawerState) {
            Scaffold(
                topBar = {
                    renderTop(drawerState, scope, player?.name)
                },
                bottomBar = {
                    renderBottom()
                },
                containerColor = Color.Transparent,
            ) { innerPadding ->
                renderContent(innerPadding)
            }
        }
    }

    @Composable
    protected fun renderTop(
        drawerState: androidx.compose.material3.DrawerState,
        scope: CoroutineScope,
        username: String?,
    ) {
        TopMenu(
            navController = navController,
            username = username ?: "",
            drawerState = drawerState,
            onDrawerOpen = {
                scope.launch {
                    drawerState.open()
                }
            },
        )
    }

    @Composable
    protected fun renderBottom() {
        BottomMenu(navController = navController)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    protected open fun renderContent(innerPadding: PaddingValues): Unit = throw Exception("Implement method")
}
