package com.example.pracainynierska.API.Exception

import com.example.pracainynierska.API.model.error_response.ValidationErrorResponse

class RequestValidationException : Exception {
    val validation: ValidationErrorResponse

    constructor(validationErrorResponse: ValidationErrorResponse) : super("Error: Validation failed") {
        this.validation = validationErrorResponse
    }
}
