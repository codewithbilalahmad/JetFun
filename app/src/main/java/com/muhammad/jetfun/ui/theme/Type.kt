package com.muhammad.jetfun.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.muhammad.jetfun.R


val lato = FontFamily(
    Font(resId = R.font.lato, weight = FontWeight.Normal)
)

val lato_bold = FontFamily(
    Font(resId = R.font.lato_bold, weight = FontWeight.Bold)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontSize = 57.sp,
        lineHeight = 64.sp,
        fontFamily = lato_bold
    ),
    displayMedium = TextStyle(
        fontSize = 45.sp,
        lineHeight = 52.sp,
        fontFamily = lato_bold
    ),
    displaySmall = TextStyle(
        fontSize = 36.sp,
        lineHeight = 44.sp,
        fontFamily = lato_bold
    ),
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        fontFamily = lato_bold
    ),
    headlineMedium = TextStyle(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        fontFamily = lato_bold,
    ),
    headlineSmall = TextStyle(
        fontSize = 26.sp,
        lineHeight = 36.sp,
        fontFamily = lato_bold,
    ),
    titleLarge = TextStyle(
        fontSize = 24.sp,
        lineHeight = 34.sp,
        fontFamily = lato_bold,
    ),

    titleMedium = TextStyle(
        fontSize = 20.sp,
        fontFamily = lato_bold,
    ),
    titleSmall = TextStyle(
        fontSize = 18.sp,
        fontFamily = lato_bold
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = lato,
        fontWeight = FontWeight.Normal,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = lato,
        fontWeight = FontWeight.Normal,
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = lato,
        fontWeight = FontWeight.Normal,
    ),
    labelLarge = TextStyle(
        fontFamily = lato,
        lineHeight = 20.0.sp,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
    ),
    labelMedium = TextStyle(
        fontFamily = lato,
        lineHeight = 16.0.sp,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
    ),
    labelSmall = TextStyle(
        fontFamily = lato,
        lineHeight = 16.0.sp,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
    )
)