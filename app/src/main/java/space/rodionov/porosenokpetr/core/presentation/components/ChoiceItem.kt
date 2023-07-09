package space.rodionov.porosenokpetr.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.ui.theme.Gray600

@Composable
fun ChoiceItem(
    modifier: Modifier = Modifier,
    textDesc: String,
    textChoice: String,
    onClick: () -> Unit
) {

    val spacing = LocalSpacing.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {

        Text(
            text = textDesc,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(spacing.spaceMedium)
                .align(Alignment.CenterStart)
        )

        Text(
            text = textChoice,
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            color = Gray600,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(spacing.spaceMedium)
                .align(Alignment.CenterEnd)        )
    }
}