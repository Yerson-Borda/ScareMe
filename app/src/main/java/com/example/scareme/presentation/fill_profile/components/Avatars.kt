package com.example.scareme.presentation.fill_profile.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.scareme.R

@Composable
fun UserAvatar(
    avatar: Bitmap?,
    onImageSelected: (Bitmap) -> Unit
){
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
                    contentDescription = stringResource(R.string.avatar),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.avatardefault),
                    contentDescription = stringResource(R.string.avatar),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

fun getBitmapFromUri(
    context: Context,
    uri: Uri
): Bitmap? {
    return context.contentResolver.openInputStream(uri)?.use { inputStream ->
        BitmapFactory.decodeStream(inputStream)
    }
}