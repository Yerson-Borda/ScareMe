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

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = balooFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        color = Color.White
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleSmall = TextStyle(
        fontFamily = balooFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.White
    )
)