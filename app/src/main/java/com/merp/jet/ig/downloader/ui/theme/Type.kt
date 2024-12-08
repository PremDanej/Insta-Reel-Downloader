package com.merp.jet.ig.downloader.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.merp.jet.ig.downloader.R

val baseFont = FontFamily(Font(R.font.poppins))

val Typography = Typography(

    titleLarge = TextStyle(
        fontFamily = baseFont,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),

    titleMedium = TextStyle(
        fontFamily = baseFont,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 10.sp,
        letterSpacing = 0.sp
    ),

    // InputText value style
    bodyLarge = TextStyle(
        fontFamily = baseFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),

    // InputText label style
    bodySmall = TextStyle(
        fontFamily = baseFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),

    // Button text style
    labelLarge = TextStyle(
        fontFamily = baseFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
)