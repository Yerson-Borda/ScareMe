package com.example.scareme.presentation.sign_in.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import com.example.scareme.R

@Composable
fun Password(
    password: String,
    error: String?,
    onPasswordChanged: (String) -> Unit,
    onImeAction: () -> Unit
) {
    var showPassword by remember { mutableStateOf(false) }

    LabeledTextField(
        value = password,
        onValueChange = onPasswordChanged,
        labelText = stringResource(R.string.password),
        error = error,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { onImeAction() }),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val contentDescription = if (showPassword)
                stringResource(R.string.hide_password)
            else stringResource(R.string.show_password)

            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(imageVector = icon, contentDescription = contentDescription, tint = androidx.compose.ui.graphics.Color.White)
            }
        }
    )
}