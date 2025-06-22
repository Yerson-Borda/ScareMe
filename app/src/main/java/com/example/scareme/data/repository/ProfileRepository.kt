package com.example.scareme.data.repository

import android.content.Context
import com.example.scareme.common.SaveTokenUtil
import com.example.scareme.data.Network.UserData.UserDataApi
import com.example.scareme.domain.Entities.RequestBodies.TopicsRequest
import com.example.scareme.domain.Entities.RequestBodies.UpdateProfRequest
import com.example.scareme.domain.Entities.RequestBodies.UserRequest
import okhttp3.MultipartBody
import retrofit2.Retrofit

class ProfileRepository(
    private val context: Context,
    private val retrofit: Retrofit = RetrofitInstance.getRetrofitInstance()
) {
    private val userDataApi: UserDataApi = retrofit.create(UserDataApi::class.java)

    private fun bearerToken(): String {
        return "Bearer ${SaveTokenUtil.getToken(context)}"
    }

    suspend fun getTopicList(): List<TopicsRequest> {
        return userDataApi.getTopicList(bearerToken())
    }

    suspend fun updateProfile(updateProfRequest: UpdateProfRequest) {
        userDataApi.updateProfile(bearerToken(), updateProfRequest)
    }

    suspend fun updateAvatar(avatar: MultipartBody.Part) {
        userDataApi.updateAvatar(bearerToken(), avatar)
    }

    suspend fun getUserList(): List<UserRequest> {
        return userDataApi.getUserList(bearerToken())
    }

    suspend fun getMyProfile(): UserRequest {
        return userDataApi.getMyProfile(bearerToken())
    }

    suspend fun getUserNamesAndAvatars(page: Int, size: Int): List<UserRequest> {
        val userList = getUserList()
        val range = calculatePageRange(page, size, userList.size)
        return if (range != null) {
            userList.subList(range.first, range.second).map { simplifyUser(it) }
        } else {
            emptyList()
        }
    }

    private fun calculatePageRange(page: Int, size: Int, totalSize: Int): Pair<Int, Int>? {
        val fromIndex = page * size
        val toIndex = minOf(fromIndex + size, totalSize)
        return if (fromIndex < totalSize) Pair(fromIndex, toIndex) else null
    }

    private fun simplifyUser(user: UserRequest): UserRequest {
        return UserRequest(
            userId = user.userId,
            name = user.name,
            aboutMyself = user.aboutMyself,
            avatar = user.avatar,
            topics = user.topics
        )
    }
}