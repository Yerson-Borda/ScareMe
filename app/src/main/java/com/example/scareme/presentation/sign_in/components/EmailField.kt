package com.example.scareme.presentation.sign_in.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.scareme.R
import com.example.scareme.presentation.sign_in.ErrorField

@Composable
fun Email(
    email: String,
    error: String?,
    onEmailChanged: (String) -> Unit,
    onImeAction: () -> Unit
){
    Column {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            value = email,
            onValueChange = { onEmailChanged(it) },
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
                    text = stringResource(R.string.e_mail),
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.White
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onImeAction()
                }
            ),
            isError = error != null
        )

        error?.let { ErrorField(it) }
    }
}