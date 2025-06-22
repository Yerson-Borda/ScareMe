package com.example.scareme.presentation.messenger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.ChatRepository
import com.example.scareme.domain.Entities.RequestBodies.MessagePagination
import com.example.scareme.domain.Entities.RequestBodies.MessageResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.HttpException
import okhttp3.RequestBody.Companion.toRequestBody

class MessengerViewModel(private val repository: ChatRepository) : ViewModel() {
    private val _messagesList = MutableStateFlow<List<MessageResponse>>(emptyList())
    val messagesList: StateFlow<List<MessageResponse>> = _messagesList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchMessages(chatId: String, limit: Int = 20, offset: Int = 0) {
        viewModelScope.launch {
            try {
                _messagesList.value = repository.getMessagesList(MessagePagination(chatId, limit, offset))
            } catch (e: HttpException) {
                handleHttpException(e)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch messages: ${e.message}"
            }
        }
    }

    fun sendMessage(chatId: String, messageText: String) {
        viewModelScope.launch {
            try {
                val messageBody = messageText.toRequestBody("text/plain".toMediaTypeOrNull())
                repository.sendMessage(chatId, messageBody)
                fetchMessages(chatId)
            } catch (e: HttpException) {
                handleHttpException(e)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to send message: ${e.message}"
            }
        }
    }

    private fun handleHttpException(exception: HttpException) {
        when (exception.code()) {
            400 -> _errorMessage.value = "An error occurred, try later"
            401, 403 -> _errorMessage.value = "Please, log in again"
            404 -> _errorMessage.value = "Resource not found"
            500 -> _errorMessage.value = "Something went wrong, please check your connection"
            else -> _errorMessage.value = "An unexpected error occurred: ${exception.message}"
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}

class MessengerViewModelFactory(private val repository: ChatRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessengerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MessengerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}