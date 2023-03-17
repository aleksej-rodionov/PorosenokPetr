package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularyEvent
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularyState
import space.rodionov.porosenokpetr.ui.theme.Gray900

@Composable
fun VocabularySearchHeader(
    query: String,
    shouldShowHint: Boolean = false,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSearchFocusChanged: (Boolean) -> Unit,
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.spaceSmall)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = { onBackClick() }
            ) {
                Image(
                    painterResource(R.drawable.ic_back),
                    contentDescription = "Back"
                )
            }

            Text(
                text = stringResource(id = R.string.collection),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6,
                color = Gray900
            )

            IconButton(
                onClick = { onMenuClick() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_more_horiz_24),
                    contentDescription = "More"
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceSmall)
        ) {

            SearchTextField(
                query = query,
                shouldShowHint = shouldShowHint,
                onFocusChanged = {
                    onSearchFocusChanged(it.isFocused)
                },
                onValueChange = {
                    onSearchTextChanged(it)
                },
                modifier = Modifier
                    .padding(spacing.spaceExtraSmall)
                    .height(28.dp)
            )
        }
    }
}