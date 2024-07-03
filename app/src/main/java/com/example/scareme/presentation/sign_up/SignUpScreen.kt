package com.example.scareme.presentation.sign_up

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.scareme.R
import com.example.scareme.common.ErrorDialog
import com.example.scareme.domain.UseCases.EmailState
import com.example.scareme.domain.UseCases.PasswordState
import com.example.scareme.navigation.AppScreens
import com.example.scareme.presentation.sign_up.components.Email
import com.example.scareme.presentation.sign_up.components.Password
import com.example.scareme.presentation.sign_up.components.RepeatPassword

@Composable
fun SignUpScreen(navController: NavController) {
    val context = LocalContext.current.applicationContext
    val viewModel: SignUpViewModel = viewModel(factory = SignUpViewModelFactory(context))

    val registerResult by remember { viewModel.signUpResult }
    val errorMessage by remember { viewModel.errorMessage }
    val showDialog = remember { mutableStateOf(false) }
    val repeatPassword by remember { viewModel.repeatPassword }

    if (registerResult != null) {
        navController.navigate(AppScreens.Registration.route)
    }

    SignUpScreenContent(
        register = { email, password, repeatPassword ->
            viewModel.register(email, password, repeatPassword)
        },
        errorMessage = errorMessage,
        repeatPassword = repeatPassword,
        onRepeatPasswordChanged = { viewModel.repeatPassword.value = it }
    )

    if (errorMessage != null) {
        showDialog.value = true
    }

    if (showDialog.value) {
        ErrorDialog(
            errorMessage = errorMessage ?: "",
            onDismiss = {
                showDialog.value = false
                viewModel.resetErrorMessage()
            }
        )
    }
}

@Composable
fun SignUpScreenContent(
    register: (email: String, password: String, repeatPassword: String) -> Unit,
    errorMessage: String?,
    repeatPassword: String,
    onRepeatPasswordChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180c14))
            .padding(horizontal = 16.dp, vertical = 40.dp),
        verticalArrangement = Arrangement.Center
    ) {
        val emailState = remember { EmailState() }
        val passwordState = remember { PasswordState() }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Title()

            val localFocusManager = LocalFocusManager.current

            Email(
                emailState.text,
                emailState.error,
                onEmailChanged = {
                    emailState.text = it
                    emailState.validate()
                },
                onImeAction = {
                    localFocusManager.moveFocus(FocusDirection.Down)
                }
            )
            Password(
                passwordState.text,
                passwordState.error,
                onPasswordChanged = {
                    passwordState.text = it
                    passwordState.validate()
                },
                onImeAction = {
                    localFocusManager.moveFocus(FocusDirection.Down)
                }
            )
            RepeatPassword(
                repeatPassword,
                errorMessage,
                onRepeatPasswordChanged = {
                    onRepeatPasswordChanged(it)
                },
                onImeAction = {
                    localFocusManager.clearFocus()
                    if (emailState.isValid() && passwordState.isValid()) {
                        register(emailState.text, passwordState.text, repeatPassword)
                    }
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Bottom
        ) {
            SignUpButton(
                enabled = emailState.isValid() && passwordState.isValid(),
                onClick = {
                    register(emailState.text, passwordState.text, repeatPassword)
                }
            )
            errorMessage?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 8.dp),

                )
            }
        }
    }
}

@Composable
fun Title() {
    Text(
        text = stringResource(R.string.sign_up),
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun ErrorField(error: String) {
    Text(
        text = error,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
        style = MaterialTheme.typography.labelSmall,
    )
}

@Composable
fun SignUpButton(enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF6921D),
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(16.dp),
        enabled = enabled,
    ) {
        Text(
            text = stringResource(R.string.sign_up),
            style = MaterialTheme.typography.displaySmall
        )
    }
}