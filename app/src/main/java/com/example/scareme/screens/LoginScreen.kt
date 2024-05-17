package com.example.scareme.screens

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scareme.ui.theme.balooFontFamily
import com.example.scareme.ui.theme.jollyFontFamily

@Composable
fun LoginScreen(){
    MainScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180c14)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "ScareMe",
                fontFamily = jollyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 100.sp,
                color = Color(0xFFF6921D)
            )
        }

        Column (
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Card (
                colors = CardDefaults.cardColors(Color(0xFFF6921D)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .width(328.dp)
                    .height(56.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp //has to be offset also
                ),
                onClick = {
                    //Go to Sign Up screen
                },
            ){
                Box (
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sign Up",
                        fontFamily = balooFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }

            Text(
                text = "Already have an account?",
                fontFamily = balooFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFFB14623),
                modifier = Modifier.padding(bottom = 5.dp)

            )

            Text(
                text = "Sign In",
                color = Color(0xFFF6921D),
                fontFamily = balooFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .background(Color(0xFF180c14))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowPreview(){
    LoginScreen()
}