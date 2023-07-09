package space.rodionov.porosenokpetr.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing

@Composable
fun PlainItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    imageVector: ImageVector? = null
) {

    val spacing = LocalSpacing.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.clickable {
            onClick()
        }
    ) {

        Text(
            text = text,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(spacing.spaceMedium )
        )

        imageVector?.let {
            Icon(
                imageVector = it,
                contentDescription = "Icon",
                modifier = Modifier.padding(spacing.spaceMedium )
            )
        }
    }
}