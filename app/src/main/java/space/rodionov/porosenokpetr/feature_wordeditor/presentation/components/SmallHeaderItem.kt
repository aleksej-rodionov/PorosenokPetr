package space.rodionov.porosenokpetr.feature_wordeditor.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing

@Composable
fun SmallHeaderItem(
    modifier: Modifier = Modifier,
    text: String,
    onIconClick: () -> Unit = {},
    imageVector: ImageVector? = null,
    @DrawableRes drawableRes: Int? = null
) {

    val spacing = LocalSpacing.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {

        Text(
            text = text,
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(spacing.spaceSmall)
        )

        if (imageVector != null) {
            IconButton(
                onClick = { onIconClick() }) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = "Icon",
                    modifier = Modifier.padding(spacing.spaceSmall)
                )
            }
        } else if (drawableRes != null) {
            IconButton(
                onClick = { onIconClick() }) {
                Icon(
                    painter = painterResource(id = drawableRes),
                    contentDescription = "Icon",
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(spacing.spaceSmall)
                )
            }
        }
    }
}