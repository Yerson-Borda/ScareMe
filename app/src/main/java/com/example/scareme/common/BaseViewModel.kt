package com.example.scareme.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import java.io.IOException

// Wanted to generalize HTTP errors, couldn't make it yet
abstract class BaseViewModel : ViewModel() {

    protected val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage

    protected fun handleHttpException(exception: HttpException) {
        _errorMessage.value = when (exception.code()) {
            400 -> "An error occurred, try later"
            401, 403 -> "Please, log in again"
            404 -> "Resource not found"
            500 -> "Something went wrong, please check your connection"
            else -> "An unexpected error occurred"
        }
    }

    protected fun handleIoException(exception: IOException) {
        _errorMessage.value = "Something went wrong, please check your connection"
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
