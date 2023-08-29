package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.presentation.components.TopBar

@Composable
fun VocabularySearchHeader(
    modifier: Modifier = Modifier,
    query: String,
    shouldShowHint: Boolean = false,
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSearchFocusChanged: (Boolean) -> Unit,
    onClearQueryClick: () -> Unit
) {

    val spacing = LocalSpacing.current

    Divider()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        TopBar(
            text = R.string.collection,
            hasMenuIcon = false,
            onBackClick = onBackClick,
            onMenuClick = onMenuClick
        )

        SearchTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = spacing.spaceSmall,
                    end = spacing.spaceSmall,
                    bottom = spacing.spaceSmall
                ),
            query = query,
            shouldShowHint = shouldShowHint,
            onFocusChanged = {
                onSearchFocusChanged(it.isFocused)
            },
            onValueChange = {
                onSearchTextChanged(it)
            },
            onClearClick = {
                onClearQueryClick()
            }
        )
    }
}

@Preview
@Composable
fun VocabularySearchHeaderPreview() {
    VocabularySearchHeader(
        query = "",
        onBackClick = {},
        onMenuClick = {},
        onSearchTextChanged = {},
        onSearchFocusChanged = {},
        onClearQueryClick = {}
    )
}