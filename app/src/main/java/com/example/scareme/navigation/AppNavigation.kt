package com.example.scareme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scareme.screens.LoginScreen
import com.example.scareme.screens.SignIn
import com.example.scareme.screens.SignUp
import com.example.scareme.screens.SplashScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {
        composable(route = AppScreens.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(AppScreens.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(AppScreens.SignUp.route){
            SignUp(navController)
        }
        composable(AppScreens.SignIn.route){
            SignIn(navController)
        }
//        composable(AppScreens.Registration.route){
//            Registration()
//        }
    }
}