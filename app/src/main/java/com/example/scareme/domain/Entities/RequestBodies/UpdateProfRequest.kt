package com.example.scareme.domain.Entities.RequestBodies

data class UpdateProfRequest (
    val name: String,
    val aboutMyself: String?,
    val topics: List<String>?
)