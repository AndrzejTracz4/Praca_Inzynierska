package com.example.pracainynierska.API.Exception

class RequestFailedException : Exception() {
    override val message: String?
        get() = "Request failed"
}