package com.example.scareme.presentation.my_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Output
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.scareme.R
import com.example.scareme.common.ErrorDialog
import com.example.scareme.domain.Entities.RequestBodies.UserRequest
import com.example.scareme.navigation.AppScreens
import com.example.scareme.presentation.bottomnav.NavigationBar
import com.example.scareme.presentation.ui.theme.balooFontFamily

@Composable
fun MyProfileScreen(navController: NavController) {
    val viewModel: MyProfileViewModel = viewModel(factory = MyProfileViewModelFactory(LocalContext.current))
    val user by viewModel.user.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.back),
                contentScale = ContentScale.FillBounds
            )
            .padding(vertical = 20.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user != null) {
            UserProfile(user!!, navController)
        } else {
            if (errorMessage != null) {
                ErrorDialog(errorMessage = errorMessage!!) {
                    navController.navigate(AppScreens.HomeScreen.route) {
                        popUpTo(navController.graph.startDestinationId)
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun UserProfile(
    user: UserRequest,
    navController: NavController
) {
    val viewModel: MyProfileViewModel = viewModel(factory = MyProfileViewModelFactory(LocalContext.current))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        contentAlignment = Alignment.CenterEnd
    ){
        IconButton(onClick = {
            viewModel.logout()
            navController.navigate(AppScreens.HomeScreen.route)
        }) {
            Icon(
                Icons.Filled.Output,
                contentDescription = stringResource(R.string.log_out),
                tint = Color(0xFFF6921D)
            )
        }
    }
    Column(
        modifier = Modifier
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = if (user.avatar != null) {
            rememberAsyncImagePainter(user.avatar)
        } else {
            painterResource(id = R.drawable.def)
        }
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(168.dp)
                .clip(CircleShape)
        )

        Text(
            text = user.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 10.dp)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Topics
    LazyColumn(
        modifier = Modifier.height(100.dp)
    ) {
        items(user.topics?.chunked(3) ?: emptyList()) { rowTopics ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowTopics.forEach { topic ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color(0xFFF6921D), shape = RoundedCornerShape(16.dp))
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = topic.title,
                            color = Color(0xFF180c14),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = balooFontFamily,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(horizontal = 10.dp, vertical = 1.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // About description
    Text(
        text = user.aboutMyself ?: "",
        color = Color.White,
        fontFamily = balooFontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        lineHeight = 35.sp
    )

    Spacer(modifier = Modifier.height(70.dp))

    Column (
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ){
        NavigationBar(navController)
    }
}