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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.scareme.R
import com.example.scareme.domain.UseCases.EmailState
import com.example.scareme.domain.UseCases.PasswordState
import com.example.scareme.navigation.AppScreens
import com.example.scareme.presentation.ui.theme.balooFontFamily

@Composable
fun SignInScreen(navController: NavController) {
    val context = LocalContext.current.applicationContext
    val viewModel: SignInViewModel = viewModel(factory = SignInViewModelFactory(context))

    val loginResult by remember { viewModel.signInResult }
    val errorMessage by remember { viewModel.errorMessage }

    if (loginResult != null) {
        navController.navigate(AppScreens.Cards.route)
    }

    SignInScreenContent(
        signIn = { email, password ->
            viewModel.login(email, password)
        },
        errorMessage = errorMessage
    )
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
            .padding(16.dp),
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
                    //If the inputted email and password matches with any in the API go to next page
                    //Else throw error
                }
            )
            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun Title(){
    Text(
        text = stringResource(R.string.sign_in),
        style = MaterialTheme.typography.titleLarge
    )
}

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
                    text = "E-mail",
                    fontFamily = balooFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
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

@Composable
fun ErrorField(error: String){
    Text(
        text = error,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
        color = Color.Red,
        fontSize = 14.sp,
        fontFamily = balooFontFamily,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Password(
    password: String,
    error: String?,
    onPasswordChanged: (String) -> Unit,
    onImeAction: () -> Unit
){
    val showPassword = remember { mutableStateOf(false) }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        value = password,
        onValueChange = { onPasswordChanged(it) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFF401c34),
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color(0xFF401c34),
            focusedTextColor = Color.White,
            unfocusedIndicatorColor =  Color.Transparent,
            focusedIndicatorColor =  Color.Transparent
        ),
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        }  else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { onImeAction() }),
        label = {
            Text(
                text = "Password",
                fontFamily = balooFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
        },
        trailingIcon = {
            if (showPassword.value){
                IconButton(onClick = { showPassword.value = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        tint = Color.White,
                        contentDescription = "hide password"
                    )
                }
            } else {
                IconButton(onClick = { showPassword.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        tint = Color.White,
                        contentDescription = "show password"
                    )
                }
            }
        },
        isError = error != null
    )

    error?.let { ErrorField(it) }
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
            fontFamily = balooFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}
