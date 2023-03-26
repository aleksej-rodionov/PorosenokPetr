package space.rodionov.porosenokpetr.feature_settings.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing

@Composable
fun HeaderItem(
    text: String,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {

        Text(
            text = text,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                horizontal = spacing.spaceMedium,
                vertical = spacing.spaceSmall
            )
        )
    }
}