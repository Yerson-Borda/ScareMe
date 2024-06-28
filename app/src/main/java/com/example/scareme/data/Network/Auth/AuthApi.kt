package com.example.scareme.data.Network.Auth

import com.example.scareme.common.NetworkConstant.LOGIN_URL
import com.example.scareme.common.NetworkConstant.LOGOUT_URL
import com.example.scareme.common.NetworkConstant.REGISTRATION_URL
import com.example.scareme.data.Network.TokenManager.Token
import com.example.scareme.domain.Entities.RequestBodies.LoginRequest
import com.example.scareme.domain.Entities.RequestBodies.RegistrationRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST(REGISTRATION_URL)
    suspend fun register(@Body registrationRequest: RegistrationRequest): Token

    @POST(LOGIN_URL)
    suspend fun login(@Body loginRequest: LoginRequest): Token

    @POST(LOGOUT_URL)
    suspend fun logout()
}