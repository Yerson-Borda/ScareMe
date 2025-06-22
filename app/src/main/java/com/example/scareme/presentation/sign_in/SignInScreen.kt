package com.example.scareme.presentation.sign_in

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
import com.example.scareme.presentation.sign_in.components.Email
import com.example.scareme.presentation.sign_in.components.Password

@Composable
fun SignInScreen(navController: NavController) {
    val context = LocalContext.current.applicationContext
    val viewModel: SignInViewModel = viewModel(factory = SignInViewModelFactory(context))

    val uiState by viewModel.uiState
    val showDialog = remember { mutableStateOf(false) }

    if (uiState is SignInUiState.Success) {
        navController.navigate(AppScreens.Cards.route)
        viewModel.resetError()
    }

    SignInScreenContent(
        signIn = { email, password -> viewModel.login(email, password) },
        errorMessage = (uiState as? SignInUiState.Error)?.message
    )

    if (uiState is SignInUiState.Error) {
        showDialog.value = true
    }

    if (showDialog.value) {
        ErrorDialog(
            errorMessage = (uiState as? SignInUiState.Error)?.message ?: "",
            onDismiss = {
                showDialog.value = false
                viewModel.resetError()
            }
        )
    }
}

@Composable
fun SignInScreenContent(
    signIn: (email: String, password: String) -> Unit,
    errorMessage: String?
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180c14))
            .padding(horizontal = 16.dp, vertical = 40.dp),
        verticalArrangement = Arrangement.Center
    ){
        val emailState = remember { EmailState() }
        val passwordState = remember { PasswordState() }
        Column (
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
                    localFocusManager.moveFocus((FocusDirection.Down))
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
                    localFocusManager.clearFocus()
                    if (emailState.isValid() && passwordState.isValid()){
                        signIn(emailState.text, passwordState.text)
                    }
                }
            )
        }
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Bottom
        ) {
            SignInButton(
                enabled = emailState.isValid() && passwordState.isValid(),
                onClick = {
                    signIn(emailState.text, passwordState.text)
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
fun Title(){
    Text(
        text = stringResource(R.string.sign_in),
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun ErrorField(error: String){
    Text(
        text = error,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
        style = MaterialTheme.typography.labelSmall,
    )
}

@Composable
fun SignInButton(enabled: Boolean, onClick: () -> Unit){
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
            text = stringResource(R.string.sign_in),
            style = MaterialTheme.typography.displaySmall
        )
    }
}