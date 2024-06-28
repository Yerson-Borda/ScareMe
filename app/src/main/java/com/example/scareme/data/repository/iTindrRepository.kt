package com.example.scareme.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.example.scareme.common.NetworkConstant.BASE_URL
import com.example.scareme.common.SaveTokenUtil
import com.example.scareme.data.Network.Auth.AuthApi
import com.example.scareme.data.Network.CardOptions.CardOptionsApi
import com.example.scareme.data.Network.ChatManager.ChatApi
import com.example.scareme.data.Network.UserData.UserDataApi
import com.example.scareme.domain.Entities.RequestBodies.GetChatRequest
import com.example.scareme.domain.Entities.RequestBodies.LoginRequest
import com.example.scareme.domain.Entities.RequestBodies.MessageResponse
import com.example.scareme.domain.Entities.RequestBodies.RegistrationRequest
import com.example.scareme.domain.Entities.RequestBodies.SendMessageRequest
import com.example.scareme.domain.Entities.RequestBodies.TopicsRequest
import com.example.scareme.domain.Entities.RequestBodies.UpdateProfRequest
import com.example.scareme.domain.Entities.RequestBodies.UserRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream

class iTindrRepository(private val context: Context) {

    val interceptor = HttpLoggingInterceptor()
    val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private val retrofit: Retrofit = getRetrofit()

    suspend fun register(email: String, password: String): String {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val service: AuthApi = retrofit.create(AuthApi::class.java)
        val registration = service.register(RegistrationRequest(email, password))
        val token = registration.accessToken
        SaveTokenUtil.saveToken(context, token)
        return token
    }

    suspend fun login(email: String, password: String): String {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val service: AuthApi = retrofit.create(AuthApi::class.java)
        val login = service.login(LoginRequest(email, password))
        val token = login.accessToken
        SaveTokenUtil.saveToken(context, token)
        return token
    }

    suspend fun getTopicList(): List<TopicsRequest> {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val token = SaveTokenUtil.getToken(context)

        val service: UserDataApi = retrofit.create(UserDataApi::class.java)
        val topics = service.getTopicList("Bearer $token")
        return topics
    }

    suspend fun updateUserProfile(updateProfRequest: UpdateProfRequest) {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val token = SaveTokenUtil.getToken(context)

        val service: UserDataApi = retrofit.create(UserDataApi::class.java)
        service.updateProfile("Bearer $token", updateProfRequest)
    }

    suspend fun updateAvatar(bitmap: Bitmap) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), byteArray)
        val body = MultipartBody.Part.createFormData("avatar", "avatar.jpg", requestFile)

        val token = SaveTokenUtil.getToken(context)
        val service: UserDataApi = retrofit.create(UserDataApi::class.java)
        service.updateAvatar("Bearer $token", body)
    }

    suspend fun getUserList(): List<UserRequest> {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val token = SaveTokenUtil.getToken(context)

        val service: UserDataApi = retrofit.create(UserDataApi::class.java)
        return service.getUserList("Bearer $token")
    }

    suspend fun getUserNamesAndAvatars(): List<UserRequest> {
        val userList = getUserList()
        return userList.map { UserRequest(it.userId, it.name, it.aboutMyself, it.avatar, it.topics) }
    }

    suspend fun likeProfile(userId: String) {
        val token = SaveTokenUtil.getToken(context)
        val cardOptionsApi: CardOptionsApi = retrofit.create(CardOptionsApi::class.java)
        cardOptionsApi.likeProfile("Bearer $token", userId)
    }

    suspend fun createChat(userId: String) {
        val token = SaveTokenUtil.getToken(context)
        val chatApi: ChatApi = retrofit.create(ChatApi::class.java)
        val requestBody = mapOf("userId" to userId)
        chatApi.createChat("Bearer $token", requestBody)
    }

    suspend fun dislikeProfile(userId: String) {
        val token = SaveTokenUtil.getToken(context)
        val cardOptionsApi: CardOptionsApi = retrofit.create(CardOptionsApi::class.java)
        cardOptionsApi.dislikeProfile("Bearer $token", userId)
    }

    suspend fun getChatsList(): List<GetChatRequest> {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val token = SaveTokenUtil.getToken(context)

        val service: ChatApi = retrofit.create(ChatApi::class.java)
        return service.getChatsList("Bearer $token")
    }

    suspend fun getMessagesList(chatId: String, limit: Int, offset: Int): List<MessageResponse> {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val token = SaveTokenUtil.getToken(context)

        val service: ChatApi = retrofit.create(ChatApi::class.java)
        return service.getMessagesList("Bearer $token", chatId, limit, offset)
    }

    suspend fun sendMessage(chatId: String, messageText: String): MessageResponse {
        val token = SaveTokenUtil.getToken(context)
        val service: ChatApi = retrofit.create(ChatApi::class.java)
        val messageBody = RequestBody.create("text/plain".toMediaTypeOrNull(), messageText)
        return service.sendMessage("Bearer $token", chatId, messageBody)
    }

    suspend fun getMyProfile(): UserRequest{
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val token = SaveTokenUtil.getToken(context)

        val service: UserDataApi = retrofit.create(UserDataApi::class.java)
        return service.getMyProfile("Bearer $token")
    }
}
