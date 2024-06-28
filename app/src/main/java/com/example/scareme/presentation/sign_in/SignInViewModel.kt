package com.example.scareme.presentation.sign_in

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.iTindrRepository
import kotlinx.coroutines.launch

class SignInViewModel(context: Context) : ViewModel() {
    private val repository: iTindrRepository = iTindrRepository(context)
    val signInResult = mutableStateOf<String?>(null)
    val errorMessage = mutableStateOf<String?>(null)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val token = repository.login(email, password)
                signInResult.value = token
                errorMessage.value = null
            } catch (e: Exception) {
                signInResult.value = null
                errorMessage.value = e.message
            }
        }
    }
}

class SignInViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignInViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
