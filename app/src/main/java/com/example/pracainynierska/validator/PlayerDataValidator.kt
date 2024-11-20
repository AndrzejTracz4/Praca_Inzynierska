package com.example.pracainynierska.validator


class PlayerDataValidator {
    fun validateUsername(username: String): String? {
        return if (username.length < 3) "Username must be at least 3 characters long" else null
    }

    fun validatePassword(password: String): String? {
        return if (password.length < 8) "Password must be at least 8 characters long" else null
    }

    fun validateEmail(email: String): String? {
        return if (!email.contains("@")) "Invalid email address" else null
    }

    fun validatePasswordsMatch(password: String, confirmPassword: String): String? {
        if (password.isEmpty()) {
            return "Password cannot be empty"
        }

        return if (password != confirmPassword) "Passwords do not match" else null
    }
}