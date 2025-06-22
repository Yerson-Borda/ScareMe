package com.example.scareme.data.repository

import android.content.Context
import com.example.scareme.common.SaveTokenUtil
import com.example.scareme.data.Network.ChatManager.ChatApi
import com.example.scareme.domain.Entities.RequestBodies.GetChatRequest
import com.example.scareme.domain.Entities.RequestBodies.MessageResponse
import okhttp3.RequestBody
import retrofit2.Retrofit
import kotlin.jvm.java

class ChatRepository(
    private val context: Context,
    private val retrofit: Retrofit = RetrofitInstance.getRetrofitInstance()
) {
    private val chatApi: ChatApi = retrofit.create(ChatApi::class.java)

    private fun bearerToken(): String {
        return "Bearer ${SaveTokenUtil.getToken(context)}"
    }

    suspend fun getChatsList(): List<GetChatRequest> {
        return chatApi.getChatsList(bearerToken())
    }

    suspend fun createChat(requestBody: Map<String, String>) {
        chatApi.createChat(bearerToken(), requestBody)
    }

    suspend fun getMessagesList(chatId: String, limit: Int, offset: Int): List<MessageResponse> {
        return chatApi.getMessagesList(bearerToken(), chatId, limit, offset)
    }

    suspend fun sendMessage(chatId: String, messageText: RequestBody): MessageResponse {
        return chatApi.sendMessage(bearerToken(), chatId, messageText)
    }
}
