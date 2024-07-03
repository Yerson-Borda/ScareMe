package com.example.scareme.presentation.my_profile.components

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.scareme.domain.Entities.RequestBodies.TopicsRequest
import com.example.scareme.domain.Entities.RequestBodies.UserRequest

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String?,
    val name: String,
    val aboutMyself: String?,
    val avatar: String?,
    val topics: List<TopicsRequest>?
) {
    companion object {
        fun fromUserRequest(user: UserRequest): UserProfileEntity {
            return UserProfileEntity(
                userId = user.userId,
                name = user.name,
                aboutMyself = user.aboutMyself,
                avatar = user.avatar,
                topics = user.topics
            )
        }
    }

    fun toUserRequest(): UserRequest {
        return UserRequest(
            userId = userId,
            name = name,
            aboutMyself = aboutMyself,
            avatar = avatar,
            topics = topics
        )
    }
}
