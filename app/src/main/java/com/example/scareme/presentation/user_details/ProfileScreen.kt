package com.example.scareme.presentation.user_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.scareme.R
import com.example.scareme.common.ErrorDialog
import com.example.scareme.presentation.bottomnav.NavigationBar
import com.example.scareme.presentation.ui.theme.balooFontFamily
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.scareme.navigation.AppScreens

@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(LocalContext.current))
    val user by viewModel.user.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchUser()
    }

    if (errorMessage != null) {
        ErrorDialog(
            errorMessage = errorMessage!!,
            onDismiss = {
                viewModel.clearErrorMessage()
                if (errorMessage == "Something went wrong, please check your connection") {
                    navController.navigate(AppScreens.HomeScreen.route)
                }
            }
        )
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
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            user?.let {
                Column(
                    modifier = Modifier
                        .padding(top = 75.dp, bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val painter = if (it.avatar != null) {
                        rememberAsyncImagePainter(it.avatar)
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
                        text = it.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.height(100.dp)
                ) {
                    items(it.topics?.chunked(3) ?: emptyList()) { rowTopics ->
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

                Text(
                    text = it.aboutMyself ?: "",
                    color = Color.White,
                    fontFamily = balooFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 35.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 31.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CircleButton(
                        onClick = {
                            scope.launch {
                                viewModel.dislikeProfile(it.userId ?: "")
                                navController.popBackStack()
                            }
                        },
                        icon = painterResource(id = R.drawable.pass)
                    )

                    CircleButton(
                        onClick = {
                            scope.launch {
                                viewModel.likeProfile(it.userId ?: "")
                                navController.popBackStack()
                            }
                        },
                        icon = painterResource(id = R.drawable.like)
                    )
                }

                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    NavigationBar(navController)
                }
            }
        }
    }
}

@Composable
fun CircleButton(
    onClick: () -> Unit,
    icon: Painter,
) {
    IconButton(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color(0xff401c34))
            .size(56.dp)
            .border(2.dp, Color.Black, CircleShape),
        onClick = onClick
    ) {
        Icon(icon, null,
            tint = Color(0xFFe84c3c),
            modifier = Modifier.size(24.dp)
        )
    }
}