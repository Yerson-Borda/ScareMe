package com.example.scareme.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.example.scareme.common.SaveTokenUtil
import com.example.scareme.data.Network.Auth.AuthApi
import com.example.scareme.data.Network.CardOptions.CardOptionsApi
import com.example.scareme.data.Network.ChatManager.ChatApi
import com.example.scareme.data.Network.UserData.UserDataApi
import com.example.scareme.domain.Entities.RequestBodies.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.io.ByteArrayOutputStream

//class iTindrRepository(private val context: Context) {
//    private val retrofit: Retrofit = RetrofitInstance.getRetrofitInstance()
//
//    private fun <T> createAuthorizedService(serviceClass: Class<T>): T {
//        val token = SaveTokenUtil.getToken(context) ?: ""
//        val service = retrofit.create(serviceClass)
//        return AuthorizedServiceWrapper(service, token).service
//    }
//
//    private data class AuthorizedServiceWrapper<T>(val service: T, val token: String)
//
//    suspend fun register(email: String, password: String): String {
//        val service = retrofit.create(AuthApi::class.java)
//        val registration = service.register(RegistrationRequest(email, password))
//        SaveTokenUtil.saveToken(context, registration.accessToken)
//        return registration.accessToken
//    }
//
//    suspend fun login(email: String, password: String): String {
//        val service = retrofit.create(AuthApi::class.java)
//        val login = service.login(LoginRequest(email, password))
//        SaveTokenUtil.saveToken(context, login.accessToken)
//        return login.accessToken
//    }
//
//    suspend fun logout() {
//        val service = createAuthorizedService(AuthApi::class.java)
//        service.logout("Bearer ${SaveTokenUtil.getToken(context)}")
//        SaveTokenUtil.saveToken(context, "")
//    }
//
//    suspend fun getTopicList(): List<TopicsRequest> {
//        val service = createAuthorizedService(UserDataApi::class.java)
//        return service.getTopicList("Bearer ${SaveTokenUtil.getToken(context)}")
//    }
//
//    suspend fun updateUserProfile(updateProfRequest: UpdateProfRequest) {
//        val service = createAuthorizedService(UserDataApi::class.java)
//        service.updateProfile("Bearer ${SaveTokenUtil.getToken(context)}", updateProfRequest)
//    }
//
//    suspend fun updateAvatar(bitmap: Bitmap) {
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
//        val byteArray = byteArrayOutputStream.toByteArray()
//        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), byteArray)
//        val body = MultipartBody.Part.createFormData("avatar", "avatar.jpg", requestFile)
//
//        val service = createAuthorizedService(UserDataApi::class.java)
//        service.updateAvatar("Bearer ${SaveTokenUtil.getToken(context)}", body)
//    }
//
//    suspend fun getUserList(): List<UserRequest> {
//        val service = createAuthorizedService(UserDataApi::class.java)
//        return service.getUserList("Bearer ${SaveTokenUtil.getToken(context)}")
//    }
//
//    suspend fun getUserNamesAndAvatars(page: Int, size: Int): List<UserRequest> {
//        val userList = getUserList()
//        val fromIndex = page * size
//        val toIndex = minOf(fromIndex + size, userList.size)
//        return if (fromIndex < userList.size) {
//            userList.subList(fromIndex, toIndex).map {
//                UserRequest(it.userId, it.name, it.aboutMyself, it.avatar, it.topics)
//            }
//        } else emptyList()
//    }
//
//    suspend fun likeProfile(userId: String) {
//        val service = createAuthorizedService(CardOptionsApi::class.java)
//        service.likeProfile("Bearer ${SaveTokenUtil.getToken(context)}", userId)
//    }
//
//    suspend fun createChat(userId: String) {
//        val service = createAuthorizedService(ChatApi::class.java)
//        val requestBody = mapOf("userId" to userId)
//        service.createChat("Bearer ${SaveTokenUtil.getToken(context)}", requestBody)
//    }
//
//    suspend fun dislikeProfile(userId: String) {
//        val service = createAuthorizedService(CardOptionsApi::class.java)
//        service.dislikeProfile("Bearer ${SaveTokenUtil.getToken(context)}", userId)
//    }
//
//    suspend fun getChatsList(): List<GetChatRequest> {
//        val service = createAuthorizedService(ChatApi::class.java)
//        return service.getChatsList("Bearer ${SaveTokenUtil.getToken(context)}")
//    }
//
//    suspend fun getMessagesList(chatId: String, limit: Int, offset: Int): List<MessageResponse> {
//        val service = createAuthorizedService(ChatApi::class.java)
//        return service.getMessagesList("Bearer ${SaveTokenUtil.getToken(context)}", chatId, limit, offset)
//    }
//
//    suspend fun sendMessage(chatId: String, messageText: String): MessageResponse {
//        val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), messageText)
//        val service = createAuthorizedService(ChatApi::class.java)
//        return service.sendMessage("Bearer ${SaveTokenUtil.getToken(context)}", chatId, requestBody)
//    }
//
//    suspend fun getMyProfile(): UserRequest {
//        val service = createAuthorizedService(UserDataApi::class.java)
//        return service.getMyProfile("Bearer ${SaveTokenUtil.getToken(context)}")
//    }
//}