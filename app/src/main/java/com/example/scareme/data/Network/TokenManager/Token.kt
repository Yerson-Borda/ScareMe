package com.example.scareme.data.Network.TokenManager

data class Token(
    val accessToken: String
)

data class RefreshTokenRequest(
    val refreshToken: String
)