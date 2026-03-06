package com.cerdita.app.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val CerditaColorScheme = lightColorScheme(
    primary = CerditaPink,
    secondary = CerditaCoral,
    tertiary = CerditaYellow,
    background = Color(0xFFFFF5F7),
    surface = Color(0xFFFFFBFD),
    onPrimary = White,
    onSecondary = White,
    onTertiary = Black,
    onBackground = Black,
    onSurface = Black
)

private val KoalitaColorScheme = lightColorScheme(
    primary = KoalaBlueGray,
    secondary = KoalaEucalyptus,
    background = Color(0xFFF0F4F0),
    surface = Color(0xFFE8EDE8),
    onPrimary = White,
    onSecondary = White,
    onBackground = Black,
    onSurface = Black
)

private val FlowersColorScheme = lightColorScheme(
    primary = FlowerPinkPastel,
    secondary = FlowerMintGreen,
    tertiary = FlowerLavender,
    background = Color(0xFFFFF5F9),
    surface = Color(0xFFF5FAF5),
    onPrimary = Black,
    onSecondary = Black,
    onTertiary = Black,
    onBackground = Black,
    onSurface = Black
)

@Composable
fun CerditaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themeType: ThemeType = ThemeType.CERDITA,
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeType) {
        ThemeType.CERDITA -> CerditaColorScheme
        ThemeType.KOALITA -> KoalitaColorScheme
        ThemeType.FLOWERS -> FlowersColorScheme
        ThemeType.MIX -> CerditaColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
