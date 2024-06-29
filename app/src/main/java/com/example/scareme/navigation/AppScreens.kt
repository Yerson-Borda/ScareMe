package com.example.scareme.navigation

sealed class AppScreens(val route: String){
    data object SplashScreen: AppScreens ("splash_screen")
    data object HomeScreen: AppScreens ("home_screen")
    data object SignUp: AppScreens ("sign_up")
    data object SignIn: AppScreens ("sign_in")
    data object Registration: AppScreens ("registration")
    data object Cards: AppScreens ("cards")
    data object Chat: AppScreens ("chat")
    data object Profile: AppScreens ("profile")
    data object Messenger: AppScreens ("messenger")
    data object MyProfile: AppScreens("my_profile")
}