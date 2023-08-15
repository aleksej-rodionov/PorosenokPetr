package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.presentation.components.PlainItem
import space.rodionov.porosenokpetr.ui.theme.Gray600
import space.rodionov.porosenokpetr.R

@Composable
fun CategoryFilterBottomDrawer(
    onDisplayAllClick: () -> Unit,
    onHideAllClick: () -> Unit
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .height(spacing.spaceLarge)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {

            TopLine(
                modifier = Modifier
                    .height(spacing.spaceExtraSmall)
                    .width(80.dp),
                color = Gray600
            )
        }
        Divider()
        PlainItem(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.expand_all),
            onClick = {
                onDisplayAllClick()
            }
        )
        Divider()
        PlainItem(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.collapse_all),
            onClick = {
                onHideAllClick()
            }
        )
    }
}