package com.example.scareme.data.Network.UserData

import com.example.scareme.common.NetworkConstant.AVATAR_URL
import com.example.scareme.common.NetworkConstant.PROFILE_URL
import com.example.scareme.common.NetworkConstant.TOPIC_URL
import com.example.scareme.common.NetworkConstant.USER_URL
import com.example.scareme.domain.Entities.RequestBodies.TopicsRequest
import com.example.scareme.domain.Entities.RequestBodies.UpdateProfRequest
import com.example.scareme.domain.Entities.RequestBodies.UserRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface UserDataApi {
    @GET(TOPIC_URL)
    suspend fun getTopicList(@Header("Authorization") token: String): List<TopicsRequest>

    @PATCH(PROFILE_URL)
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body updateProfRequest: UpdateProfRequest
    )

    @Multipart
    @POST(AVATAR_URL)
    suspend fun updateAvatar(
        @Header("Authorization") token: String,
        @Part avatar: MultipartBody.Part
    )

    @GET(USER_URL)
    suspend fun getUserList(@Header("Authorization") token: String): List<UserRequest>

    @GET(PROFILE_URL)
    suspend fun getMyProfile(@Header("Authorization") token: String): UserRequest
}