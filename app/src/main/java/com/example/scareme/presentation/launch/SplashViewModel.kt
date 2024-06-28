package com.example.scareme.presentation.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    fun startSplashScreenTimer(onTimeout: () -> Unit) {
        viewModelScope.launch {
            delay(2500)
            onTimeout()
        }
    }
}