package com.example.scareme.presentation.main_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.CardOptionsRepository
import com.example.scareme.data.repository.ChatRepository
import com.example.scareme.data.repository.ProfileRepository
import com.example.scareme.domain.Entities.RequestBodies.Pagination
import com.example.scareme.domain.Entities.RequestBodies.UserRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MatchViewModel(
    private val cardOptionsRepository: CardOptionsRepository,
    private val profileRepository: ProfileRepository,
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _profiles = MutableStateFlow<List<UserRequest>>(emptyList())
    val profiles: StateFlow<List<UserRequest>> = _profiles

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private var currentPage = 0
    private val pageSize = 10

    init {
        fetchProfiles()
    }

    fun fetchProfiles() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val userList = profileRepository.getUserNamesAndAvatars(Pagination(currentPage, pageSize))
                if (userList.isNotEmpty()) {
                    _profiles.value = _profiles.value + userList
                    currentPage++
                }
            } catch (e: HttpException) {
                handleHttpException(e)
            } catch (e: IOException) {
                _errorMessage.value = "Something went wrong, please check your connection"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun likeProfile(userId: String) {
        viewModelScope.launch {
            try {
                cardOptionsRepository.likeProfile(userId)
                chatRepository.createChat(mapOf("userId" to userId))
                removeProfile(userId)
                fetchProfilesIfNeeded()
            } catch (e: HttpException) {
                handleHttpException(e)
            } catch (e: IOException) {
                _errorMessage.value = "Something went wrong, please check your connection"
            }
        }
    }

    fun dislikeProfile(userId: String) {
        viewModelScope.launch {
            try {
                cardOptionsRepository.dislikeProfile(userId)
                removeProfile(userId)
                fetchProfilesIfNeeded()
            } catch (e: HttpException) {
                handleHttpException(e)
            } catch (e: IOException) {
                _errorMessage.value = "Something went wrong, please check your connection"
            }
        }
    }

    private fun removeProfile(userId: String) {
        _profiles.value = _profiles.value.filterNot { it.userId == userId }
    }

    private fun fetchProfilesIfNeeded() {
        if (_profiles.value.size < pageSize) {
            fetchProfiles()
        }
    }

    private fun handleHttpException(exception: HttpException) {
        _errorMessage.value = when (exception.code()) {
            400 -> "An error occurred, try later"
            401, 403 -> "Please, log in again"
            404 -> "Resource not found"
            500 -> {
                _errorMessage.value = "Something went wrong, please check your connection"
                null
            }
            else -> "An unexpected error occurred"
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
            return MatchViewModel(
                cardOptionsRepository = CardOptionsRepository(context),
                profileRepository = ProfileRepository(context),
                chatRepository = ChatRepository(context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}