package space.rodionov.porosenokpetr.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import space.rodionov.porosenokpetr.core.presentation.Dimensions
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing

private val DarkColorPalette = darkColors(
    primary = Gray850,
    primaryVariant = Purple700,
    secondary = Blue,

    background = Gray900,
    surface = Gray900,
    onPrimary = TransparentGray15,
    onSecondary = Gray100,
    onBackground = Gray100,
    onSurface = Gray100,
)

private val LightColorPalette = lightColors(
    primary = Blue, //Text selection drops
    primaryVariant = Purple700,
    secondary = Blue, //CheckBoxes

    background = Gray200, // screens' backgrounds
    surface = Gray100, // drawers' backgrounds
    onPrimary = Gray900,
    onSecondary = Gray900,
    onBackground = Gray900,
    onSurface = Gray900,
)

@Composable
fun PorosenokPetrTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(LocalSpacing provides Dimensions()) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}