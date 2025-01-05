package com.example.pracainynierska.ui_view_components.view

import com.example.pracainynierska.view_model.LoginViewModel

class TestView(loginViewModel: LoginViewModel) : AbstractView(loginViewModel) {
    private fun renderFoo() {
        TODO("Not yet implemented")
    }

    fun renderContentX() {
        renderTop()
        renderDrawer()
        //todo content
        renderBottom()
    }

    override fun renderContent() {
        renderFoo()
    }
}