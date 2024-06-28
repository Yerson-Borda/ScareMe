package com.example.scareme.presentation.bottomnav

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scareme.R
import com.example.scareme.navigation.AppScreens

@Composable
fun NavigationBar(navController: NavController){
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ){
        Icon(
            painter = painterResource(id = R.drawable.homeoff),
            contentDescription = null,
            modifier = Modifier
                .size(26.dp)
                .clickable { navController.navigate(AppScreens.Cards.route) },
            tint = Color(0xFFB14623)
        )
        Icon(
            painter = painterResource(id = R.drawable.chatoff),
            contentDescription = null,
            modifier = Modifier
                .size(26.dp)
                .clickable { navController.navigate(AppScreens.Chat.route) },
            tint = Color(0xFFB14623)
        )
        Icon(
            painter = painterResource(id = R.drawable.profileoff),
            contentDescription = null,
            modifier = Modifier
                .size(26.dp)
                .clickable { navController.navigate(AppScreens.MyProfile.route) },
            tint = Color(0xFFB14623)
        )
    }
}