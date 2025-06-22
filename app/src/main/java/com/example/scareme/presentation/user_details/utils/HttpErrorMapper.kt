package com.example.scareme.presentation.user_details.utils

object HttpErrorMapper {
    fun mapHttpError(code: Int): String {
        return when (code) {
            400 -> "An error occurred, try later"
            401, 403 -> "Please, log in again"
            404 -> "Resource not found"
            500 -> "Something went wrong, please check your connection"
            else -> "An unexpected error occurred"
        }
    }
}