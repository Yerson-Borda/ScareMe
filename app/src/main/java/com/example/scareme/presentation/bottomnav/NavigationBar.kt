package com.example.scareme.presentation.bottomnav

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.scareme.R
import com.example.scareme.navigation.AppScreens

@Composable
fun NavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.Transparent
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = if (currentRoute == AppScreens.Cards.route) R.drawable.homeon else R.drawable.homeoff),
                    contentDescription = null,
                    modifier = Modifier.size(if (currentRoute == AppScreens.Cards.route) 40.dp else 26.dp),
                    tint = Color.Unspecified
                )
            },
            selected = currentRoute == AppScreens.Cards.route,
            onClick = { navController.navigate(AppScreens.Cards.route) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFFB14623),
                unselectedIconColor = Color(0xFFB14623),
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = if (currentRoute == AppScreens.Chat.route) R.drawable.chaton else R.drawable.chatoff),
                    contentDescription = null,
                    modifier = Modifier.size(if (currentRoute == AppScreens.Chat.route) 40.dp else 26.dp),
                    tint = Color.Unspecified
                )
            },
            selected = currentRoute == AppScreens.Chat.route,
            onClick = { navController.navigate(AppScreens.Chat.route) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFFB14623),
                unselectedIconColor = Color(0xFFB14623),
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = if (currentRoute == AppScreens.MyProfile.route) R.drawable.profileon else R.drawable.profileoff),
                    contentDescription = null,
                    modifier = Modifier.size(if (currentRoute == AppScreens.MyProfile.route) 40.dp else 26.dp),
                    tint = Color.Unspecified
                )
            },
            selected = currentRoute == AppScreens.MyProfile.route,
            onClick = { navController.navigate(AppScreens.MyProfile.route) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFFB14623),
                unselectedIconColor = Color(0xFFB14623),
                indicatorColor = Color.Transparent
            )
        )
    }
}