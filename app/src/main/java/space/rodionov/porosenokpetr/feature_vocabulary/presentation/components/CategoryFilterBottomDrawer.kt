package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.ui.theme.Gray600

@Composable
fun CategoryFilterBottomDrawer(
    modifier: Modifier = Modifier,
    onDisplayAllClick: () -> Unit,
    onHideAllClick: () -> Unit
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.spaceMedium),
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
        BottomDrawerItem(
            text = "Display all categories",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onDisplayAllClick()
                }
        )
        Divider()
        BottomDrawerItem(
            text = "Hide all categories",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onHideAllClick()
                }
        )
    }
}