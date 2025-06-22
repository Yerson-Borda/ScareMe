package com.example.scareme.presentation.sign_in.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.scareme.presentation.sign_in.ErrorField

@Composable
fun LabeledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    error: String?,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            value = value,
            onValueChange = onValueChange,
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
                    text = labelText,
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.White
                )
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            isError = error != null,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon
        )

        error?.let { ErrorField(it) }
    }
}