package com.example.scareme.navigation

sealed class AppScreens(val route: String){
    data object SplashScreen: AppScreens ("splash_screen")
    data object LoginScreen: AppScreens ("login_screen")
    data object SignUn: AppScreens ("sign_up")
    data object SignIn: AppScreens ("sign_in")
    data object Registration: AppScreens ("registration")
}