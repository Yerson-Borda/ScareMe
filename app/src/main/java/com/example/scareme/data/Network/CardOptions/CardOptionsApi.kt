package com.example.scareme.data.Network.CardOptions

import com.example.scareme.common.NetworkConstant.DISLIKE_URL
import com.example.scareme.common.NetworkConstant.LIKE_URL
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CardOptionsApi {
    @POST(LIKE_URL)
    suspend fun likeProfile(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    )

    @POST(DISLIKE_URL)
    suspend fun dislikeProfile(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    )
}