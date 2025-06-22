package com.example.scareme.presentation.fill_profile

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.ProfileRepository
import com.example.scareme.domain.Entities.RequestBodies.TopicsRequest
import com.example.scareme.domain.Entities.RequestBodies.UpdateProfRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class UserInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ProfileRepository(getApplication<Application>().applicationContext)

    private val _topics = MutableStateFlow<List<TopicsRequest>>(emptyList())
    val topics: StateFlow<List<TopicsRequest>> = _topics

    init {
        fetchTopics()
    }

    private fun fetchTopics() {
        viewModelScope.launch {
            try {
                val repository = ProfileRepository(getApplication<Application>().applicationContext)
                val fetchedTopics = repository.getTopicList()
                _topics.value = fetchedTopics
                Log.d("UserInfoViewModel", "Fetched topics: $fetchedTopics")
            } catch (e: Exception) {
                Log.e("UserInfoViewModel", "Error fetching topics", e)
            }
        }
    }

    fun saveUserProfile(updateProfRequest: UpdateProfRequest, avatar: Bitmap?) {
        viewModelScope.launch {
            try {
                coroutineScope {
                    val updateProfileDeferred = async { repository.updateProfile(updateProfRequest) }

                    if (avatar != null) {
                        val stream = ByteArrayOutputStream()
                        avatar.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val requestFile = stream.toByteArray().toRequestBody("image/*".toMediaTypeOrNull())
                        val body = MultipartBody.Part.createFormData("avatar", "avatar.png", requestFile)

                        val updateAvatarDeferred = async { repository.updateAvatar(body) }
                        updateProfileDeferred.await()
                        updateAvatarDeferred.await()
                    } else {
                        updateProfileDeferred.await()
                    }
                }
            } catch (e: Exception) {
                Log.e("UserInfoViewModel", "Error saving user profile", e)
            }
        }
    }
}