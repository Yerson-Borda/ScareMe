package com.example.scareme.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.scareme.R

val balooFontFamily = FontFamily (
    Font(R.font.baloo_paaji_regular, FontWeight.Normal),
    Font(R.font.baloo_paaji_bold, FontWeight.Bold)
)
val jollyFontFamily = FontFamily (
    Font(R.font.jolly_lodger_regular, FontWeight.Normal)
)

val Typography = Typography(
    //ScareMe Big Title
    titleLarge = TextStyle(
        fontFamily = jollyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 100.sp,
        color = Color(0xFFF6921D)
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    // Why are you scary?
    titleSmall = TextStyle(
        fontFamily = balooFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.White
    ),
    // Text field Errors
    labelSmall = TextStyle(
        fontFamily = balooFontFamily,
        fontSize = 14.sp,
        color = Color.Red,
        fontWeight = FontWeight.Bold
    ),
    //Title in Sign In - Sign Up -
    titleMedium = TextStyle(
        fontFamily = balooFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        color = Color.White
    ),
    //Most often texts
    displaySmall = TextStyle(
        fontFamily = balooFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
)