package com.example.pracainynierska.API.Exception

class RequestValidationException : Exception {
    var errorDetails : Map<String, String>

    constructor(errorDetails: Map<String, String>) : super("Error: Validation failed") {
        this.errorDetails = errorDetails
    }

    fun getErrorDetailsMessage() : String {
        var message = ""
        for ((key, value) in errorDetails) {
            message += "$value\n"
        }
        return message
    }
}
