package com.example.scareme.presentation.launch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.scareme.R
import com.example.scareme.navigation.AppScreens
import com.example.scareme.presentation.ui.theme.jollyFontFamily

@Composable
fun SplashScreen(navController: NavController, splashViewModel: SplashViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        splashViewModel.startSplashScreenTimer {
            navController.popBackStack()
            navController.navigate(AppScreens.HomeScreen.route)
        }
    }

    Splash()
}

@Composable
fun Splash() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = linearGradientBrush()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "ScareMe Logo",
            modifier = Modifier.size(396.dp)
        )

        Spacer(modifier = Modifier.height(34.dp))

        Text(
            text = "ScareMe",
            fontFamily = jollyFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 100.sp,
            color = Color(0xFFF6921D)
        )
    }
}

@Composable
fun linearGradientBrush(): Brush {
    return Brush.linearGradient(
        colors = listOf(Color(0xFF130912), Color(0xFF3E1C33)),
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )
}