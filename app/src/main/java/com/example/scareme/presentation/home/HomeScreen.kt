package com.example.scareme.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scareme.R
import com.example.scareme.navigation.AppScreens
import com.example.scareme.presentation.home.components.BubbleElement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180c14))
            .padding(vertical = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.title),
                    style = MaterialTheme.typography.titleLarge
                )

                BubbleElement()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                colors = CardDefaults.cardColors(Color(0xFFF6921D)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .width(328.dp)
                    .height(56.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                onClick = {
                    navController.navigate(route = AppScreens.SignUp.route)
                },
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.sign_up),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.Black
                    )
                }
            }

            Text(
                text = stringResource(R.string.user_already),
                style = MaterialTheme.typography.displaySmall,
                color = Color(0xFFB14623),
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Text(
                text = stringResource(R.string.sign_in),
                color = Color(0xFFF6921D),
                style = MaterialTheme.typography.displaySmall,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .background(Color(0xFF180c14))
                    .clickable {
                        navController.navigate(route = AppScreens.SignIn.route)
                    }
            )
        }
    }
}