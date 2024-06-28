package com.example.scareme.common

object NetworkConstant {
    //Auth
    const val BASE_URL = "http://itindr.mcenter.pro:8092/api/mobile/v1/"
    const val LOGIN_URL = "auth/login"  //POST
    const val REGISTRATION_URL = "auth/register"  //POST
    const val LOGOUT_URL = "auth/logout"  //DELETE
    const val REFRESH_URL = "auth/refresh"  //POST

    //Profile
    const val PROFILE_URL = "profile" //GET - PATCH
    const val AVATAR_URL = "profile/avatar"

    //Topic
    const val TOPIC_URL = "topic" //GET

    //Users
    const val USER_URL = "user/feed" //GET

    //Like - dislike
    const val LIKE_URL = "user/{userId}/like" //POST
    const val DISLIKE_URL = "user/{userId}/dislike" //POST

    //Chat
    const val CHAT_URL = "chat" //get list of chats (GET) - create (POST)
    const val MESSAGES_URL = "chat/{chatId}/message" //get list of messages (GET) - send message (POST)
}

