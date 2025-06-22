package com.example.scareme.data.repository

import android.content.Context
import com.example.scareme.common.SaveTokenUtil
import com.example.scareme.data.Network.Auth.AuthApi
import com.example.scareme.domain.Entities.RequestBodies.LoginRequest
import com.example.scareme.domain.Entities.RequestBodies.RegistrationRequest
import retrofit2.Retrofit

class AuthRepository(
    private val context: Context,
    private val retrofit: Retrofit = RetrofitInstance.getRetrofitInstance()
) {
    private val authApi: AuthApi = retrofit.create(AuthApi::class.java)

    suspend fun register(email: String, password: String): String {
        val registration = authApi.register(RegistrationRequest(email, password))
        val token = registration.accessToken
        SaveTokenUtil.saveToken(context, token)
        return token
    }

    suspend fun login(email: String, password: String): String {
        val login = authApi.login(LoginRequest(email, password))
        val token = login.accessToken
        SaveTokenUtil.saveToken(context, token)
        return token
    }

    suspend fun logout() {
        val token = SaveTokenUtil.getToken(context) ?: ""
        authApi.logout("Bearer $token")
        SaveTokenUtil.saveToken(context, "")
    }
}