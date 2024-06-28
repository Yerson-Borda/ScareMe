package com.example.scareme.presentation.messenger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.iTindrRepository
import com.example.scareme.domain.Entities.RequestBodies.MessageResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MessengerViewModel(private val repository: iTindrRepository) : ViewModel() {
    private val _messagesList = MutableStateFlow<List<MessageResponse>>(emptyList())
    val messagesList: StateFlow<List<MessageResponse>> = _messagesList

    fun fetchMessages(chatId: String, limit: Int = 50, offset: Int = 0) {
        viewModelScope.launch {
            _messagesList.value = repository.getMessagesList(chatId, limit, offset)
        }
    }

    fun sendMessage(chatId: String, messageText: String) {
        viewModelScope.launch {
            repository.sendMessage(chatId, messageText)
            fetchMessages(chatId)
        }
    }
}

class MessengerViewModelFactory(private val repository: iTindrRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessengerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MessengerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
