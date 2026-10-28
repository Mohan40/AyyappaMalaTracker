package com.example.ayyappamala.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val AmoledDarkColorScheme = darkColorScheme(
    primary = SaffronAmoledPrimary,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF332100),
    onPrimaryContainer = GoldAmoledAccent,
    secondary = GoldAmoledAccent,
    onSecondary = Color.Black,
    background = AmoledBackground,
    onBackground = OnAmoledSurface,
    surface = AmoledSurface,
    onSurface = OnAmoledSurface,
    surfaceVariant = AmoledCard,
    onSurfaceVariant = OnAmoledVariant
)

private val LightColorScheme = lightColorScheme(
    primary = SaffronPrimary,
    onPrimary = Color.White,
    primaryContainer = CardBackgroundLight,
    onPrimaryContainer = OnSurfaceLight,
    secondary = SaffronDark,
    onSecondary = Color.White,
    background = DeepBackground,
    onBackground = OnSurfaceLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = Color(0xFFF5F0EB),
    onSurfaceVariant = Color(0xFF53433F)
)

@Composable
fun AyyappaMalaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> AmoledDarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
