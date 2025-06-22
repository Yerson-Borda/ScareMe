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

data class NavItem(
    val route: String,
    val iconOn: Int,
    val iconOff: Int
)

@Composable
fun NavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navItems = listOf(
        NavItem(AppScreens.Cards.route, R.drawable.homeon, R.drawable.homeoff),
        NavItem(AppScreens.Chat.route, R.drawable.chaton, R.drawable.chatoff),
        NavItem(AppScreens.MyProfile.route, R.drawable.profileon, R.drawable.profileoff)
    )

    NavigationBar(containerColor = Color.Transparent) {
        navItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = if (selected) item.iconOn else item.iconOff),
                        contentDescription = null,
                        modifier = Modifier.size(if (selected) 40.dp else 26.dp),
                        tint = Color.Unspecified
                    )
                },
                selected = selected,
                onClick = { navController.navigate(item.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFB14623),
                    unselectedIconColor = Color(0xFFB14623),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}