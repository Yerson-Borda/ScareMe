package com.example.scareme.domain.Entities.RequestBodies

data class RegistrationRequest (
    val email: String,
    val password: String,
    val repass: String? = null
)
