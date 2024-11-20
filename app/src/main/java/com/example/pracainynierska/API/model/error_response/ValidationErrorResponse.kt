package com.example.pracainynierska.API.model.error_response

import kotlinx.serialization.Serializable

@Serializable
data class ValidationErrorResponse(
    val violations: List<Violation>,
    val title: String,
    val detail: String,
)