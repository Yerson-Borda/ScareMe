package com.example.scareme.presentation.user_details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.CardOptionsRepository
import com.example.scareme.data.repository.ProfileRepository
import com.example.scareme.domain.Entities.RequestBodies.UserRequest
import com.example.scareme.presentation.user_details.utils.HttpErrorMapper.mapHttpError
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
                _errorMessage.value = mapHttpError(e.code())
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
                _errorMessage.value = mapHttpError(e.code())
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
                _errorMessage.value = mapHttpError(e.code())
            } catch (e: IOException) {
                _errorMessage.value = "Something went wrong, please check your connection"
            }
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