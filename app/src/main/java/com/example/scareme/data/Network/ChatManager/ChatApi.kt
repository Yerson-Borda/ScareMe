package com.example.scareme.data.Network.ChatManager

import com.example.scareme.common.NetworkConstant.CHAT_URL
import com.example.scareme.common.NetworkConstant.MESSAGES_URL
import com.example.scareme.domain.Entities.RequestBodies.GetChatRequest
import com.example.scareme.domain.Entities.RequestBodies.MessageResponse
import com.example.scareme.domain.Entities.RequestBodies.SendMessageRequest
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatApi {
    @GET(CHAT_URL)
    suspend fun getChatsList(@Header("Authorization") token: String): List<GetChatRequest>

    @POST(CHAT_URL)
    suspend fun createChat(@Header("Authorization") token: String, @Body requestBody: Map<String, String>)

    @GET(MESSAGES_URL)
    suspend fun getMessagesList(
        @Header("Authorization") token: String,
        @Path("chatId") chatId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<MessageResponse>

    @Multipart
    @POST(MESSAGES_URL)
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Path("chatId") chatId: String,
        @Part("messageText") messageText: RequestBody
    ): MessageResponse
}