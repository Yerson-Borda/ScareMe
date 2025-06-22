package com.example.scareme.data.repository

import android.content.Context
import com.example.scareme.common.SaveTokenUtil
import com.example.scareme.data.Network.CardOptions.CardOptionsApi
import retrofit2.Retrofit

class CardOptionsRepository(
    private val context: Context,
    private val retrofit: Retrofit = RetrofitInstance.getRetrofitInstance()
) {
    private val api: CardOptionsApi = retrofit.create(CardOptionsApi::class.java)

    private fun bearerToken(): String {
        return "Bearer ${SaveTokenUtil.getToken(context)}"
    }

    suspend fun likeProfile(userId: String) {
        api.likeProfile(bearerToken(), userId)
    }

    suspend fun dislikeProfile(userId: String) {
        api.dislikeProfile(bearerToken(), userId)
    }
}
