package com.example.pracainynierska.API.model.error_response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val type: String,
    val title: String,
    val detail: String,
)