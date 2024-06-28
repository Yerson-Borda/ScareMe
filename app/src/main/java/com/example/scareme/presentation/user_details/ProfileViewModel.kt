package com.example.scareme.presentation.user_details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.iTindrRepository
import com.example.scareme.domain.Entities.RequestBodies.UserRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: iTindrRepository) : ViewModel() {

    private val _user = MutableStateFlow<UserRequest?>(null)
    val user: StateFlow<UserRequest?> = _user

    fun fetchUser() {
        viewModelScope.launch {
            val userList = repository.getUserList()
            if (userList.isNotEmpty()) {
                _user.value = userList[0]
            }
        }
    }

    fun likeProfile(userId: String) {
        viewModelScope.launch {
            repository.likeProfile(userId)
            fetchUser()
        }
    }

    fun dislikeProfile(userId: String) {
        viewModelScope.launch {
            repository.dislikeProfile(userId)
            fetchUser()
        }
    }
}

class ProfileViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(iTindrRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
