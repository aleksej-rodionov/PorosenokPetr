package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.layout.*
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
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.ui.theme.Gray900

@Composable
fun VocabularySearchHeader(
    modifier: Modifier = Modifier,
    query: String,
    shouldShowHint: Boolean = false,
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSearchFocusChanged: (Boolean) -> Unit,
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = spacing.spaceSmall,
                end = spacing.spaceSmall,
                top = spacing.spaceExtraSmall
            )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Gray900
                )
            }

            Text(
                text = stringResource(id = R.string.collection),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6,
                color = Gray900
            )

            IconButton(
                onClick = onMenuClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_more_horiz_24),
                    contentDescription = "More",
                    tint = Gray900
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = spacing.spaceSmall,
                    end = spacing.spaceSmall,
                    bottom = spacing.spaceSmall
                )
        ) {

            SearchTextField(
                query = query,
                shouldShowHint = shouldShowHint,
                onFocusChanged = {
                    onSearchFocusChanged(it.isFocused)
                },
                onValueChange = {
                    onSearchTextChanged(it)
                }
            )
        }
    }
}