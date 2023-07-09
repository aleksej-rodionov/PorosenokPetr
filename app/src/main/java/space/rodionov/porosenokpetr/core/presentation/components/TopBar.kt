package space.rodionov.porosenokpetr.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    text: Int,
    hasMenuIcon: Boolean = false,
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit = {},
) {

    val spacing = LocalSpacing.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = spacing.spaceSmall,
                end = spacing.spaceSmall,
                top = spacing.spaceExtraSmall
            )
    ) {

        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onBackClick
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Back",
                tint = MaterialTheme.colors.onBackground
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = text), //todo localize
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onBackground
        )

        if (hasMenuIcon) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = onMenuClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_more_horiz_24),
                    contentDescription = "More",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar(
        text = R.string.settings,
        hasMenuIcon = true,
        onBackClick = {},
        onMenuClick = {}
    )
}