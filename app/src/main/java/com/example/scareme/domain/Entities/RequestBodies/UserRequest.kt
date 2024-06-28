package com.example.scareme.domain.Entities.RequestBodies

data class UserRequest(
    val userId: String?,
    val name: String,
    val aboutMyself: String?,
    val avatar: String?,
    val topics: List<TopicsRequest>?
)
