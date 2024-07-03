package com.example.scareme.presentation.my_profile.components

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(profile: UserProfileEntity)

    @Query("SELECT * FROM user_profile LIMIT 1")
    suspend fun getUserProfile(): UserProfileEntity?

    @Query("DELETE FROM user_profile")
    suspend fun clearUserProfile()
}
