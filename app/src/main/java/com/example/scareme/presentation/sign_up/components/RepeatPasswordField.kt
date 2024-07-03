package com.example.scareme.presentation.sign_up.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.scareme.R
import com.example.scareme.presentation.sign_up.ErrorField

@Composable
fun RepeatPassword(
    repeatPassword: String,
    errorMessage: String?,
    onRepeatPasswordChanged: (String) -> Unit,
    onImeAction: () -> Unit
) {
    val showPassword = remember { mutableStateOf(false) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        value = repeatPassword,
        onValueChange = { onRepeatPasswordChanged(it) },
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
        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { onImeAction() }),
        label = {
            Text(
                text = stringResource(R.string.repeat_password),
                style = MaterialTheme.typography.displaySmall,
                color = Color.White
            )
        },
        trailingIcon = {
            if (showPassword.value) {
                IconButton(onClick = { showPassword.value = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        tint = Color.White,
                        contentDescription = stringResource(R.string.hide_password)
                    )
                }
            } else {
                IconButton(onClick = { showPassword.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        tint = Color.White,
                        contentDescription = stringResource(R.string.show_password)
                    )
                }
            }
        },
        isError = errorMessage != null && errorMessage == stringResource(R.string.passwords_do_not_match)
    )

    if (errorMessage == stringResource(R.string.passwords_do_not_match)) {
        ErrorField(errorMessage)
    }
}