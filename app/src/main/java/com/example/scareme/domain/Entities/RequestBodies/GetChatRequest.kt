package com.example.scareme.domain.Entities.RequestBodies

data class GetChatRequest(
    val chat: Chat,
    val lastMessage: LastMessage
)

data class Chat(
    val id: String,
    val title: String,
    val avatar: String
)

data class LastMessage(
    val id: String,
    val text: String,
    val createdAt: String,
    val attachments: List<String>,
    val user: User
)

data class User(
    val userId: String,
    val name: String,
    val aboutMyself: String,
    val avatar: String
)

data class MessageResponse(
    val id: String,
    val text: String,
    val createdAt: String,
    val attachments: List<String>,
    val user: User
)

data class SendMessageRequest(
    val messageText: String,
    val attachments: List<String>? = null
)