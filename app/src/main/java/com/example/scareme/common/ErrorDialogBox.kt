package com.example.scareme.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.scareme.R
import com.example.scareme.presentation.ui.theme.balooFontFamily

@Composable
fun ErrorDialog(errorMessage: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = true),
        containerColor = Color(0xFF3C0753),
        title = {
            Text(
                text = stringResource(R.string.error),
                fontFamily = balooFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            ) },
        text = {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.displaySmall
            )
        },
        confirmButton = {
            Divider(
                color = Color(0xFF68244c),
                thickness = 3.dp,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            TextButton(onClick = { onDismiss() }) {
                Text(
                    stringResource(R.string.ok),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color(0xFFF6921D))
            }
        }
    )
}