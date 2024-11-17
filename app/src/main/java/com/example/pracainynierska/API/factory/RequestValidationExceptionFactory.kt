package com.example.pracainynierska.API.factory

import com.example.pracainynierska.API.Exception.RequestValidationException

class RequestValidationExceptionFactory {

    fun create(errorString: String): RequestValidationException {
        return RequestValidationException(parseErrorString(errorString))
    }

    private fun parseErrorString(errorString: String): Map<String, String> {
        val errorMap = mutableMapOf<String, String>()

        errorString.lines().forEach { line ->
            val parts = line.split(":", limit = 2)
            if (parts.size == 2) {
                val key = parts[0].trim()
                val value = parts[1].trim()
                errorMap[key] = value
            }
        }

        return errorMap
    }
}