package com.example.scareme.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.scareme.navigation.AppNavigation
import com.example.scareme.presentation.ui.theme.ScareMeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScareMeTheme {
                AppNavigation()
            }
        }
    }
}

