package com.example.pracainynierska.validator

import com.example.pracainynierska.R


class PlayerDataValidator {
    fun validateUsername(username: String): Int {
        return if (username.length < 3) R.string.validation_username_must_be_at_least_3_characters_long else 0
    }

    fun validatePassword(password: String): Int {
        return if (password.length < 8) R.string.validation_password_must_be_at_least_8_characters_long else 0
    }

    fun validateEmail(email: String): Int {
        return if (!email.contains("@")) R.string.validation_invalid_email_format else 0
    }

    fun validatePasswordsMatch(password: String, confirmPassword: String): Int {
        if (password.isEmpty()) {
            return R.string.validation_password_cannot_be_empty
        }

        return if (password != confirmPassword) R.string.validation_passwords_does_not_match else 0
    }
}