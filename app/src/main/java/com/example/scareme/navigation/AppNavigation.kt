package com.example.scareme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.scareme.presentation.sign_in.SignInScreen
import com.example.scareme.presentation.home.HomeScreen
import com.example.scareme.presentation.launch_screen.SplashScreen
import com.example.scareme.presentation.main_screen.Main
import com.example.scareme.presentation.chat.ChatScreen
import com.example.scareme.presentation.messenger.MessengerScreen
import com.example.scareme.presentation.my_profile.MyProfileScreen
import com.example.scareme.presentation.user_details.ProfileScreen
import com.example.scareme.presentation.sign_up.SignUpScreen
import com.example.scareme.presentation.fill_profile.UserInfo

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {
        composable(route = AppScreens.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(AppScreens.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(AppScreens.SignIn.route){
            SignInScreen(navController)
        }
        composable(AppScreens.SignUp.route){
            SignUpScreen(navController)
        }
        composable(AppScreens.Registration.route){
            UserInfo(navController)
        }
        composable(AppScreens.Cards.route){
            Main(navController)
        }
        composable(AppScreens.Chat.route){
            ChatScreen(navController)
        }
        composable(AppScreens.Profile.route){
            ProfileScreen(navController)
        }
        composable(
            "${AppScreens.Messenger.route}/{chatId}/{avatar}/{title}",
            arguments = listOf(
                navArgument("chatId") { type = NavType.StringType },
                navArgument("avatar") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            val avatar = backStackEntry.arguments?.getString("avatar") ?: ""
            val title = backStackEntry.arguments?.getString("title") ?: ""
            MessengerScreen(navController, chatId, avatar, title)
        }
        composable(AppScreens.MyProfile.route){
            MyProfileScreen(navController)
        }
    }
}