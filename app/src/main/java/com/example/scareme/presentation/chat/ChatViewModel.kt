package com.example.scareme.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.ChatRepository
import com.example.scareme.domain.Entities.RequestBodies.GetChatRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ChatViewModel(private val repository: ChatRepository) : ViewModel() {
    private val _chatList = MutableStateFlow<List<GetChatRequest>>(emptyList())
    val chatList: StateFlow<List<GetChatRequest>> = _chatList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchChats()
    }

    private fun fetchChats() {
        viewModelScope.launch {
            try {
                _chatList.value = repository.getChatsList()
            } catch (e: IOException) {
                _errorMessage.value = "Something went wrong, please check your connection"
            } catch (e: HttpException) {
                handleHttpException(e)
            } catch (e: Exception) {
                _errorMessage.value = "An unexpected error occurred"
            }
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

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}

class ChatViewModelFactory(private val repository: ChatRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
