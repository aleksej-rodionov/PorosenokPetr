package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.presentation.components.TopBar
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

    Divider()

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        TopBar(
            text = R.string.collection,
            onBackClick = onBackClick,
            onMenuClick = onMenuClick
        )

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