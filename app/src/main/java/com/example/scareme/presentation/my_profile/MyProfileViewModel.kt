package com.example.scareme.presentation.my_profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.AuthRepository
import com.example.scareme.data.repository.ProfileRepository
import com.example.scareme.domain.Entities.RequestBodies.UserRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MyProfileViewModel(
    private val repository: ProfileRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _user = MutableStateFlow<UserRequest?>(null)
    val user: StateFlow<UserRequest?> = _user

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchUser() {
        viewModelScope.launch {
            try {
                val userData = repository.getMyProfile()
                _user.value = userData
            } catch (e: IOException) {
                _errorMessage.value = "Something went wrong, please check your connection"
            } catch (e: HttpException) {
                handleHttpException(e)
            } catch (e: Exception) {
                Log.e("MyProfileViewModel", "Error fetching user data", e)
                _errorMessage.value = "An unexpected error occurred"
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    private fun handleHttpException(exception: HttpException) {
        _errorMessage.value = when (exception.code()) {
            400 -> "An error occurred, try later"
            401, 403 -> "Please, log in again"
            404 -> "Resource not found"
            500 -> "Something went wrong, please try again later"
            else -> "An unexpected error occurred"
        }
    }
}

class MyProfileViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyProfileViewModel(
                repository = ProfileRepository(context),
                authRepository = AuthRepository(context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}