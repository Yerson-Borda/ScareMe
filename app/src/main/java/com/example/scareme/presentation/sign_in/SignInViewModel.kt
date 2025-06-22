package com.example.scareme.presentation.sign_in

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.AuthRepository
import com.example.scareme.domain.Entities.RequestBodies.LoginRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignInViewModel(context: Context) : ViewModel() {
    private val repository: AuthRepository = AuthRepository(context)
    val uiState = mutableStateOf<SignInUiState>(SignInUiState.Idle)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            uiState.value = SignInUiState.Loading
            try {
                val token = repository.login(LoginRequest(email, password))
                uiState.value = SignInUiState.Success(token)
            } catch (e: HttpException) {
                uiState.value = SignInUiState.Error(
                    when (e.code()) {
                        400 -> "An error occurred, try later"
                        404 -> "User not found, verify the credentials"
                        500 -> "Something went wrong, check your internet"
                        else -> "Unexpected error occurred"
                    }
                )
            } catch (e: Exception) {
                uiState.value = SignInUiState.Error("Something went wrong, check your connection")
            }
        }
    }

    fun resetError() {
        uiState.value = SignInUiState.Idle
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

sealed class SignInUiState {
    object Idle : SignInUiState()
    object Loading : SignInUiState()
    data class Success(val token: String) : SignInUiState()
    data class Error(val message: String) : SignInUiState()
}
