package com.example.scareme.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.scareme.data.repository.iTindrRepository
import com.example.scareme.presentation.bottomnav.NavigationBar
import com.example.scareme.presentation.ui.theme.balooFontFamily
import com.example.scareme.domain.Entities.RequestBodies.GetChatRequest
import com.example.scareme.navigation.AppScreens
import java.net.URLEncoder

@Composable
fun ChatScreen(navController: NavController) {
    val context = LocalContext.current.applicationContext
    val repository = iTindrRepository(context)
    val chatViewModel: ChatViewModel = viewModel(factory = ChatViewModelFactory(repository))

    val chatList by chatViewModel.chatList.collectAsState()

    val errorMessage by chatViewModel.errorMessage.collectAsState()

    if (errorMessage != null) {
        ErrorDialog(
            errorMessage = errorMessage!!,
            onDismiss = {
                chatViewModel.clearErrorMessage()
                if (errorMessage == "Something went wrong, please check your connection") {
                    navController.navigate(AppScreens.HomeScreen.route)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180c14))
            .padding(vertical = 20.dp, horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.last),
            color = Color.White,
            fontSize = 44.sp,
            fontFamily = balooFontFamily,
            fontWeight = FontWeight.Bold
        )

        UserItem(chatList)

        Text(
            text = stringResource(R.string.messages),
            color = Color.White,
            fontSize = 44.sp,
            fontFamily = balooFontFamily,
            fontWeight = FontWeight.Bold
        )

        MessageItem(chatList, navController)

        if (chatList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.you_don_t_have_messages_yet),
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = { navController.navigate(AppScreens.Cards.route) },
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color(0xFF180c14),
                        containerColor = Color(0xFFF6921D)),
                ) {
                    Text(
                        stringResource(R.string.find_people),
                        style = MaterialTheme.typography.displaySmall
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

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            NavigationBar(navController)
        }
    }
}

@Composable
fun UserItem(chatList: List<GetChatRequest>) {
    LazyRow(
        modifier = Modifier.padding(top = 10.dp, bottom = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(chatList) { chatRequest ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = chatRequest.chat.avatar),
                    contentDescription = null,
                    modifier = Modifier
                        .size(82.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = chatRequest.chat.title,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontFamily = balooFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MessageItem(chatList: List<GetChatRequest>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(chatList) { chatRequest ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val encodedAvatar = URLEncoder.encode(chatRequest.chat.avatar, "UTF-8")
                        navController.navigate(
                            "${AppScreens.Messenger.route}/${chatRequest.chat.id}/$encodedAvatar/${chatRequest.chat.title}"
                        )
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                val lastMessage = chatRequest.lastMessage
                if (lastMessage != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = chatRequest.chat.avatar),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(
                            text = lastMessage.text,
                            color = Color.White,
                            fontSize = 17.sp,
                            fontFamily = balooFontFamily,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp, bottom = 15.dp)
                        )
                        Divider(
                            color = Color(0xFFB14623),
                            thickness = 1.dp,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                } else {
                    Text(
                        text = "No more messages",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = balooFontFamily,
                        modifier = Modifier.padding(start = 16.dp, bottom = 15.dp)
                    )
                }
            }
        }
    }
}