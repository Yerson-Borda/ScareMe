package com.example.scareme.data.Network.Auth

import com.example.scareme.data.Network.TokenManager.Token
import com.example.scareme.domain.Entities.RequestBodies.LoginRequest
import com.example.scareme.domain.Entities.RequestBodies.RegistrationRequest

class AuthRepository(
    private val api: AuthApi
) {
    suspend fun register(registrationRequest: RegistrationRequest): Token {
        return api.register(registrationRequest)
    }

    suspend fun login(loginRequest: LoginRequest): Token {
        return api.login(loginRequest)
    }

    suspend fun logout() {
        api.logout()
    }
}