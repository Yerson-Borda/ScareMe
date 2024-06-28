package com.example.scareme.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.iTindrRepository
import com.example.scareme.domain.Entities.RequestBodies.GetChatRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel(private val repository: iTindrRepository) : ViewModel() {
    private val _chatList = MutableStateFlow<List<GetChatRequest>>(emptyList())
    val chatList: StateFlow<List<GetChatRequest>> = _chatList

    init {
        fetchChats()
    }

    private fun fetchChats() {
        viewModelScope.launch {
            _chatList.value = repository.getChatsList()
        }
    }
}

class ChatViewModelFactory(private val repository: iTindrRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
