package com.example.scareme.presentation.sign_up

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.AuthRepository
import com.example.scareme.domain.Entities.RequestBodies.RegistrationRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import androidx.compose.runtime.State

class SignUpViewModel(private val context: Context) : ViewModel() {
    private val repository: AuthRepository = AuthRepository(context)

    private val _uiState = mutableStateOf<SignUpUiState>(SignUpUiState.Idle)
    val uiState: State<SignUpUiState> = _uiState

    val repeatPassword = mutableStateOf("")

    fun register(email: String, password: String, repeatPassword: String) {
        if (password != repeatPassword) {
            _uiState.value = SignUpUiState.Error("Passwords do not match")
            return
        }

        _uiState.value = SignUpUiState.Loading
        viewModelScope.launch {
            try {
                val token = repository.register(RegistrationRequest(email, password))
                _uiState.value = SignUpUiState.Success(token)
            } catch (e: Exception) {
                _uiState.value = SignUpUiState.Error(
                    when (e) {
                        is HttpException -> when (e.code()) {
                            400 -> "An error occurred, try later"
                            409 -> "The user already exists"
                            500 -> "Something went wrong, check your internet"
                            else -> "Unexpected error occurred"
                        }
                        else -> "Something went wrong, check your connection"
                    }
                )
            }
        }
    }

    fun resetErrorMessage() {
        if (_uiState.value is SignUpUiState.Error) {
            _uiState.value = SignUpUiState.Idle
        }
    }

    fun resetResult() {
        if (_uiState.value is SignUpUiState.Success) {
            _uiState.value = SignUpUiState.Idle
        }
    }
}

sealed class SignUpUiState {
    object Idle : SignUpUiState()
    object Loading : SignUpUiState()
    data class Success(val token: String) : SignUpUiState()
    data class Error(val message: String) : SignUpUiState()
}

class SignUpViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}