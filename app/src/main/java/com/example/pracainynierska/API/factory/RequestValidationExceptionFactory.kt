package com.example.pracainynierska.API.factory

import com.example.pracainynierska.API.Exception.RequestValidationException
import com.example.pracainynierska.API.model.error_response.ValidationErrorResponse

class RequestValidationExceptionFactory {
    fun create(validationErrorResponse: ValidationErrorResponse): RequestValidationException =
        RequestValidationException(validationErrorResponse)
}
