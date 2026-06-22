package com.guiltfreechain.app.ui.theme

import android.app.Activity
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

private val LightColorScheme = lightColorScheme(
    primary = PrimaryTeal,
    onPrimary = OnPrimaryTeal,
    primaryContainer = PrimaryContainer,
    secondary = SecondaryBlue,
    onSecondary = OnSecondaryBlue,
    background = BackgroundWarm,
    onBackground = OnSurface,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = Surface,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Outline,
    error = ErrorSoft,
    errorContainer = ErrorContainer
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryTeal,
    onPrimary = OnPrimaryTeal,
    primaryContainer = PrimaryContainer,
    secondary = SecondaryBlue,
    onSecondary = OnSecondaryBlue,
    background = Color(0xFF1A1C19),
    onBackground = Color(0xFFFAFAF5),
    surface = Color(0xFF2D3436),
    onSurface = Color(0xFFFAFAF5),
    surfaceVariant = Color(0xFF2D3436),
    onSurfaceVariant = Color(0xFFEEECE9),
    outline = Color(0xFF6E7976),
    error = ErrorSoft,
    errorContainer = ErrorContainer
)

@Composable
fun GuiltFreeChainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}