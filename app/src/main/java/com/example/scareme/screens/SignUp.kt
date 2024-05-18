package com.example.scareme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.scareme.ui.theme.balooFontFamily

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(navController: NavController){
    Scaffold(
        topBar = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = Color.White,
                    contentDescription = "Go back",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
            }
        }
    ) {
        SignUp(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavController){

    var email by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180c14))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = "Sign Up",
                fontFamily = balooFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                color = Color.White
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                value = email,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF401c34),
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF401c34),
                    focusedTextColor = Color.White,
                    unfocusedIndicatorColor =  Color.Transparent,
                    focusedIndicatorColor =  Color.Transparent
                ),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                onValueChange = { email = it },
                placeholder = {
                    Text(
                        text = "E-mail",
                        fontFamily = balooFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                value = password,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF401c34),
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF401c34),
                    focusedTextColor = Color.White,
                    unfocusedIndicatorColor =  Color.Transparent,
                    focusedIndicatorColor =  Color.Transparent
                ),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeholder = {
                    Text(
                        text = "Password",
                        fontFamily = balooFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                value = email, //I didn't change this yet
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF401c34),
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF401c34),
                    focusedTextColor = Color.White,
                    unfocusedIndicatorColor =  Color.Transparent,
                    focusedIndicatorColor =  Color.Transparent
                ),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                onValueChange = { email = it },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeholder = {
                    Text(
                        text = "Repeat Password",
                        fontFamily = balooFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            )
        }

        Column (
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Bottom,
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
                    //Save results and go to registration
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
        }
    }
}