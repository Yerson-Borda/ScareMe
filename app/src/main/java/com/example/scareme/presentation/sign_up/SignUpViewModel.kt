package com.example.scareme.presentation.sign_up

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.iTindrRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignUpViewModel(
    private val context: Context,
    private val repository: iTindrRepository = iTindrRepository(context)
) : ViewModel() {
//    private val repository: iTindrRepository = iTindrRepository(context)
    val signUpResult = mutableStateOf<String?>(null)
    val errorMessage = mutableStateOf<String?>(null)
    val repeatPassword = mutableStateOf("")

    fun register(email: String, password: String, repeatPassword: String) {
        if (password != repeatPassword) {
            errorMessage.value = "Passwords do not match"
            return
        }

        viewModelScope.launch {
            try {
                val token = repository.register(email, password)
                signUpResult.value = token
                errorMessage.value = null
            } catch (e: Exception) {
                signUpResult.value = null
                errorMessage.value = when (e) {
                    is HttpException -> when (e.code()) {
                        400 -> "An error occurred, try later"
                        409 -> "The user already exists"
                        500 -> "Something went wrong, check your internet"
                        else -> "Unexpected error occurred"
                    }
                    else -> "Something went wrong, check your connection"
                }
            }
        }
    }

    fun resetErrorMessage() {
        errorMessage.value = null
    }
}

class SignUpViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}