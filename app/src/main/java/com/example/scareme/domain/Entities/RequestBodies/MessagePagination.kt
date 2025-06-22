package com.example.scareme.domain.Entities.RequestBodies

data class MessagePagination(val chatId: String, val limit: Int, val offset: Int)

