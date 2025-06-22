package com.example.scareme.presentation.user_details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.CardOptionsRepository
import com.example.scareme.data.repository.ProfileRepository
import com.example.scareme.domain.Entities.RequestBodies.UserRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ProfileViewModel(
    private val repository: ProfileRepository,
    private val cardOptionsRepository: CardOptionsRepository
) : ViewModel() {

    private val _user = MutableStateFlow<UserRequest?>(null)
    val user: StateFlow<UserRequest?> = _user

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchUser() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val userList = repository.getUserList()
                if (userList.isNotEmpty()) {
                    _user.value = userList[0]
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
                fetchUser()
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
                fetchUser()
            } catch (e: HttpException) {
                handleHttpException(e)
            } catch (e: IOException) {
                _errorMessage.value = "Something went wrong, please check your connection"
            }
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

class ProfileViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(
                repository = ProfileRepository(context),
                cardOptionsRepository = CardOptionsRepository(context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}