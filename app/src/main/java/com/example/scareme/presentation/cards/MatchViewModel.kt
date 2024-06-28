package com.example.scareme.presentation.cards

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.iTindrRepository
import com.example.scareme.domain.Entities.RequestBodies.UserRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MatchViewModel(private val repository: iTindrRepository) : ViewModel() {

    private val _profiles = MutableStateFlow<List<UserRequest>>(emptyList())
    val profiles: StateFlow<List<UserRequest>> = _profiles

    init {
        fetchProfiles()
    }

    fun fetchProfiles() {
        viewModelScope.launch {
            val userList = repository.getUserNamesAndAvatars()
            _profiles.value = userList
        }
    }

    fun likeProfile(userId: String) {
        viewModelScope.launch {
            repository.likeProfile(userId)
            repository.createChat(userId)
            fetchProfiles()
        }
    }

    fun dislikeProfile(userId: String) {
        viewModelScope.launch {
            repository.dislikeProfile(userId)
            fetchProfiles()
        }
    }
}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
            return MatchViewModel(iTindrRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}