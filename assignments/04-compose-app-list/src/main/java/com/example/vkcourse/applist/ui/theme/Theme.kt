package com.example.vkcourse.applist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF2167D5),
    onPrimary = Color.White,
    background = Color(0xFFF3F5F9),
    onBackground = Color(0xFF17212F),
    surface = Color.White,
    onSurface = Color(0xFF17212F),
    onSurfaceVariant = Color(0xFF687383)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF82B1FF),
    onPrimary = Color(0xFF08234C),
    background = Color(0xFF11151B),
    onBackground = Color(0xFFE5EAF2),
    surface = Color(0xFF1D232C),
    onSurface = Color(0xFFE5EAF2),
    onSurfaceVariant = Color(0xFFB4BECC)
)

@Composable
fun CatalogTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
        content = content
    )
}
