package com.example.scareme.presentation.sign_in

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.iTindrRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

open class SignInViewModel(context: Context) : ViewModel(), ISignInViewModel {
    private val repository: iTindrRepository = iTindrRepository(context)
    override val signInResult = mutableStateOf<String?>(null)
    override val errorMessage = mutableStateOf<String?>(null)

    override fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val token = repository.login(email, password)
                signInResult.value = token
                errorMessage.value = null
            } catch (e: HttpException) {
                signInResult.value = null
                errorMessage.value = when (e.code()) {
                    400 -> "An error occurred, try later"
                    404 -> "User not found, verify the credentials"
                    500 -> "Something went wrong, check your internet"
                    else -> "Unexpected error occurred"
                }
            } catch (e: Exception) {
                signInResult.value = null
                errorMessage.value = "Something went wrong, check your connection"
            }
        }
    }

    override fun resetErrorMessage() {
        errorMessage.value = null
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
