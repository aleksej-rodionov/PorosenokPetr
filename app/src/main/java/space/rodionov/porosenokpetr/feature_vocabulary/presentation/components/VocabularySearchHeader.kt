package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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