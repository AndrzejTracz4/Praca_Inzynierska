package com.example.pracainynierska.ui_view_components.view

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.pracainynierska.ui.components.ModalDrawer
import com.example.pracainynierska.view_model.AbstractViewModel


abstract class AbstractView(
    protected val viewModel: AbstractViewModel
) {
    @Composable
    protected fun render() {
        ModalDrawer(navController = navController, drawerState = drawerState) {
            Scaffold(
                topBar = {
                    renderTop()
                },
                bottomBar = {
                    renderBottom()
                },
                containerColor = Color.Transparent
            )
            renderDrawer()
            renderContent()
        }
    }

    protected fun renderTop() {
        val player = viewModel.getPlayer()
        TODO("Not yet implemented")
    }

    protected fun renderBottom() {
        TODO("Not yet implemented")
    }

    protected fun renderDrawer() {
        TODO("Not yet implemented")
    }

    abstract fun renderContent()
}