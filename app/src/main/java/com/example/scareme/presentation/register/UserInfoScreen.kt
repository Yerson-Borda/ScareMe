package com.example.scareme.presentation.register

import UserInfoViewModel
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.scareme.R
import com.example.scareme.domain.Entities.RequestBodies.TopicsRequest
import com.example.scareme.domain.Entities.RequestBodies.UpdateProfRequest
import com.example.scareme.presentation.ui.theme.balooFontFamily
import com.example.scareme.navigation.AppScreens

@Composable
fun UserInfo(navController: NavController, viewModel: UserInfoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val fetchedTopics by viewModel.topics.collectAsState()
    var name by remember { mutableStateOf("") }
    var aboutMyself by remember { mutableStateOf<String?>(null) }
    var avatar by remember { mutableStateOf<Bitmap?>(null) }
    val topics = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180c14))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title()
        UserAvatar(avatar) { bitmap -> avatar = bitmap }
        Info(name, aboutMyself, onNameChange = { name = it }, onAboutMyselfChange = { aboutMyself = it })
        Topics(fetchedTopics, topics)
        SaveButton(navController, viewModel, UpdateProfRequest(name, aboutMyself, topics.ifEmpty { null }), avatar, name.isNotEmpty())
    }
}

@Composable
fun Title() {
    Text(
        text = "Why are you scary?",
        fontFamily = balooFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.White
    )
}

@Composable
fun UserAvatar(avatar: Bitmap?, onImageSelected: (Bitmap) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val bitmap = getBitmapFromUri(context, it)
            bitmap?.let { selectedImage ->
                onImageSelected(selectedImage)
            }
        }
    }

    Column(
        Modifier.padding(top = 16.dp, bottom = 29.dp)
    ) {
        Box(
            Modifier
                .size(150.dp)
                .border(BorderStroke(4.dp, Color(0xFFF6921D)), CircleShape)
                .clip(CircleShape)
                .clickable { launcher.launch("image/*") }
        ) {
            if (avatar != null) {
                Image(
                    bitmap = avatar.asImageBitmap(),
                    contentDescription = "Avatar photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.avatardefault),
                    contentDescription = "Avatar photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun Info(name: String, aboutMyself: String?, onNameChange: (String) -> Unit, onAboutMyselfChange: (String?) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        value = name,
        onValueChange = onNameChange,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFF401c34),
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color(0xFF401c34),
            focusedTextColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        label = {
            Text(
                text = "Name",
                fontFamily = balooFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
        },
    )

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(128.dp),
        value = aboutMyself ?: "",
        onValueChange = onAboutMyselfChange,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFF401c34),
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color(0xFF401c34),
            focusedTextColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp),
        label = {
            Text(
                text = "About",
                fontFamily = balooFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
        },
    )

    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun Topics(fetchedTopics: List<TopicsRequest>, topics: MutableList<String>) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Party Topics",
            fontFamily = balooFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            items(fetchedTopics.chunked(3)) { rowItems ->
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    items(rowItems) { topic ->
                        TopicItem(topic, topics)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun TopicItem(topic: TopicsRequest, topics: MutableList<String>) {
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor = if (isSelected) Color(0xFFF6921D) else Color(0xFF180c14)
    val textColor = if (isSelected) Color(0xFF180c14) else Color(0xFFF6921D)

    Box(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                isSelected = !isSelected
                if (isSelected) {
                    topics.add(topic.id)
                } else {
                    topics.remove(topic.id)
                }
            }
            .border(BorderStroke(2.dp, Color(0xFFF6921D)), shape = RoundedCornerShape(16.dp))
    ) {
        Text(
            text = topic.title,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = balooFontFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun SaveButton(
    navController: NavController,
    viewModel: UserInfoViewModel,
    updateProfRequest: UpdateProfRequest,
    avatar: Bitmap?,
    isEnabled: Boolean
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        onClick = {
            viewModel.saveUserProfile(updateProfRequest, avatar)
            navController.navigate(AppScreens.Cards.route)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF6921D),
            disabledContainerColor = Color.Gray,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(16.dp),
        enabled = isEnabled
    ) {
        Text(
            text = "Save",
            fontFamily = balooFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return context.contentResolver.openInputStream(uri)?.use { inputStream ->
        BitmapFactory.decodeStream(inputStream)
    }
}

