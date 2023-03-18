package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import space.rodionov.porosenokpetr.ui.theme.TransparentGray15

@Composable
fun TopLine(
    modifier: Modifier = Modifier,
    color: Color
) {

    Canvas(modifier = modifier) {

        drawRoundRect(
            color = color,
            size = size,
            cornerRadius = CornerRadius(100f)
        )
    }
}