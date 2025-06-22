package com.example.scareme.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
            PrimaryCardButton(
                text = stringResource(R.string.sign_up),
                onClick = { navController.navigate(AppScreens.SignUp.route) }
            )

            PlainText(
                text = stringResource(R.string.user_already),
                color = Color(0xFFB14623),
                padding = PaddingValues(bottom = 5.dp)
            )

            UnderlinedClickableText(
                text = stringResource(R.string.sign_in),
                color = Color(0xFFF6921D),
                onClick = { navController.navigate(AppScreens.SignIn.route) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryCardButton(text: String, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(Color(0xFFF6921D)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .width(328.dp)
            .height(56.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = text,
                style = MaterialTheme.typography.displaySmall,
                color = Color.Black
            )
        }
    }
}

@Composable
fun PlainText(text: String, color: Color, padding: PaddingValues = PaddingValues(0.dp)) {
    Text(
        text = text,
        style = MaterialTheme.typography.displaySmall,
        color = color,
        modifier = Modifier.padding(padding)
    )
}

@Composable
fun UnderlinedClickableText(text: String, color: Color, onClick: () -> Unit) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.displaySmall,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .padding(bottom = 5.dp)
            .background(Color(0xFF180c14))
            .clickable(onClick = onClick)
    )
}