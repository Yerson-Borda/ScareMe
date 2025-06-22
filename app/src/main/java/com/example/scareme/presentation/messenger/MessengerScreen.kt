package com.example.scareme.presentation.messenger

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scareme.R
import com.example.scareme.presentation.ui.theme.balooFontFamily
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.scareme.common.ErrorDialog
import com.example.scareme.data.repository.ChatRepository
import com.example.scareme.domain.Entities.RequestBodies.MessageResponse
import com.example.scareme.navigation.AppScreens
import kotlinx.coroutines.delay

@Composable
fun MessengerScreen(
    navController: NavController,
    chatId: String,
    avatar: String,
    title: String
) {
    val context = LocalContext.current.applicationContext
    val repository = ChatRepository(context)
    val messengerViewModel: MessengerViewModel = viewModel(factory = MessengerViewModelFactory(repository))

    LaunchedEffect(Unit) {
        messengerViewModel.fetchMessages(chatId)
    }

    LaunchedEffect(Unit) {
        while (true) {
            messengerViewModel.fetchMessages(chatId)
            delay(30000)
        }
    }

    val messagesList by messengerViewModel.messagesList.collectAsState()
    val errorMessage by messengerViewModel.errorMessage.collectAsState()

    if (errorMessage != null) {
        ErrorDialog(
            errorMessage = errorMessage!!,
            onDismiss = {
                messengerViewModel.clearErrorMessage()
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
            .padding(horizontal = 10.dp)
            .padding(top = 45.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        From(avatar, title, navController)

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true
        ) {
            items(messagesList) { message ->
                MessageCloud(message, title)
            }
        }

        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SendMessage { messageText ->
                messengerViewModel.sendMessage(chatId, messageText)
            }
        }
    }
}

@Composable
fun From(
    avatar: String,
    title: String,
    navController: NavController
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    navController.navigate(AppScreens.Chat.route)
                },
            tint = Color.White
        )
        Image(
            painter = rememberAsyncImagePainter(model = avatar),
            contentDescription = null,
            modifier = Modifier
                .size(82.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = title,
            color = Color.White,
            fontSize = 30.sp,
            fontFamily = balooFontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 25.dp)
        )
    }
}

@Composable
fun MessageCloud(message: MessageResponse, chatTitle: String) {
    val isDifferentUser = message.user.name != chatTitle
    val backgroundColor = if (isDifferentUser) Color(0xFFF6921D) else Color(0xFF401c34)
    val textColor = if (isDifferentUser) Color.Black else Color.White
    val shape = if (isDifferentUser) {
        RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 20.dp)
    } else {
        RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 20.dp)
    }
    val alignment = if (isDifferentUser) Alignment.End else Alignment.Start

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor, shape = shape)
                .padding(horizontal = 4.dp, vertical = 4.dp)
                .width(254.dp)
        ) {
            Text(
                text = message.text,
                color = textColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = balooFontFamily,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 8.dp)
            )
        }
        Text(
            text = message.createdAt,
            color = Color(0xFF909093),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun SendMessage(onSend: (String) -> Unit) {
    var messageText by remember { mutableStateOf("") }

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {
        TextField(
            value = messageText,
            onValueChange = { messageText = it },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFF401c34),
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color(0xFF401c34),
                focusedTextColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(26.dp),
        )
        SendButton(
            onClick = {
                if (messageText.isNotBlank()) {
                    onSend(messageText)
                    messageText = ""
                }
            },
            icon = painterResource(id = R.drawable.send),
        )
    }
}

@Composable
fun SendButton(
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
            tint = Color(0xFFF6921D),
            modifier = Modifier.size(24.dp)
        )
    }
}