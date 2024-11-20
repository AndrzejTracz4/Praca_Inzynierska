package com.example.pracainynierska.API.model.error_response

import kotlinx.serialization.Serializable

@Serializable
data class Violation (
    val propertyPath: String,
    val message: String
)