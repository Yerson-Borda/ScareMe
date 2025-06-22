package com.example.scareme.presentation.sign_in.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.scareme.R

@Composable
fun Email(
    email: String,
    error: String?,
    onEmailChanged: (String) -> Unit,
    onImeAction: () -> Unit
) {
    LabeledTextField(
        value = email,
        onValueChange = onEmailChanged,
        labelText = stringResource(R.string.e_mail),
        error = error,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        keyboardActions = androidx.compose.foundation.text.KeyboardActions(onNext = { onImeAction() })
    )
}