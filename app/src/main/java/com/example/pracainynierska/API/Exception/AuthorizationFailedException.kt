package com.example.pracainynierska.API.Exception

class AuthorizationFailedException : Exception() {
    override val message: String?
        get() = "Authorization Failed"
}
