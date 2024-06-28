package com.example.scareme.domain.UseCases

class PasswordState: TextFieldState (
    validator = ::isPasswordValid,
    errorMessage = { passwordErrorMessage() }
)

fun isPasswordValid(password: String) = password.length >= 8

fun passwordErrorMessage() = "Password is invalid"