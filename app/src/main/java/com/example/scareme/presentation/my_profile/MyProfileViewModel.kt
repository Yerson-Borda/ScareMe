package com.example.scareme.presentation.my_profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.iTindrRepository
import com.example.scareme.domain.Entities.RequestBodies.UserRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyProfileViewModel(private val repository: iTindrRepository) : ViewModel() {
    private val _user = MutableStateFlow<UserRequest?>(null)
    val user: StateFlow<UserRequest?> = _user

    fun fetchUser() {
        viewModelScope.launch {
            try {
                val userData = repository.getMyProfile()
                _user.value = userData
            } catch (e: Exception) {
                // Handle exceptions (e.g., log them)
                Log.e("MyProfileViewModel", "Error fetching user data", e)
            }
        }
    }
}

class MyProfileViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyProfileViewModel(iTindrRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
