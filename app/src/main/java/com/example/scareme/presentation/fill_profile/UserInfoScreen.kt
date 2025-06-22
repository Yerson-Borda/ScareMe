package com.example.scareme.presentation.fill_profile

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.scareme.R
import com.example.scareme.domain.Entities.RequestBodies.UpdateProfRequest
import com.example.scareme.presentation.ui.theme.balooFontFamily
import com.example.scareme.navigation.AppScreens
import com.example.scareme.presentation.fill_profile.components.Topics
import com.example.scareme.presentation.fill_profile.components.UserAvatar

@Composable
fun UserInfo(
    navController: NavController,
    viewModel: UserInfoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
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
        Info(
            name,
            aboutMyself,
            onNameChange = { name = it },
            onAboutMyselfChange = { aboutMyself = it }
        )
        Topics(
            fetchedTopics,
            topics
        )
        SaveButton(
            navController,
            viewModel,
            UpdateProfRequest(name, aboutMyself, topics.ifEmpty { null }),
            avatar,
            name.isNotEmpty())
    }
}

@Composable
fun Title() {
    Text(
        text = stringResource(R.string.why_are_you_scary),
        style = MaterialTheme.typography.titleSmall
    )
}

@Composable
fun Info(
    name: String,
    aboutMyself: String?,
    onNameChange: (String) -> Unit,
    onAboutMyselfChange: (String?) -> Unit
) {
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
                text = stringResource(R.string.name),
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
                text = stringResource(R.string.about),
                style = MaterialTheme.typography.displaySmall,
                color = Color.White
            )
        },
    )

    Spacer(modifier = Modifier.height(24.dp))
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
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(16.dp),
        enabled = isEnabled
    ) {
        Text(
            text = stringResource(R.string.save),
            style = MaterialTheme.typography.displaySmall
        )
    }
}