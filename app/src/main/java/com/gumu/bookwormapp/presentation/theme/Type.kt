package com.gumu.bookwormapp.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gumu.bookwormapp.R

private val nunitoSans = FontFamily(
    Font(R.font.nunito_sans, FontWeight.Normal),
    Font(R.font.nunito_sans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.nunito_sans_light, FontWeight.Light),
    Font(R.font.nunito_sans_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.nunito_sans_semibold, FontWeight.SemiBold),
    Font(R.font.nunito_sans_semibold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.nunito_sans_bold, FontWeight.Bold),
    Font(R.font.nunito_sans_bold_italic, FontWeight.Bold, FontStyle.Italic)
)

val NunitoSansTypography = Typography(
    bodySmall = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp,
//        letterSpacing = 0.2.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
//        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        lineHeight = 14.sp,
//        letterSpacing = 0.4.sp
    ),
    labelMedium = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
//        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
//        letterSpacing = 0.4.sp
    ),
    titleSmall = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
//        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
//        letterSpacing = 0.4.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
//        letterSpacing = 0.2.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
//        letterSpacing = 0.2.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
//        letterSpacing = 0.2.sp
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)
