package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
import space.rodionov.porosenokpetr.ui.theme.Blue

@Composable
fun VocabularyChipGroup(
    modifier: Modifier = Modifier,
    categories: List<VocabularyItem.CategoryUi> = emptyList(),
    onSelectedChanged: (VocabularyItem.CategoryUi, Boolean) -> Unit
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = modifier
    ) {

        Spacer(modifier = Modifier.padding(spacing.spaceExtraSmall))

        FlowRow(
            mainAxisSpacing = 12.dp,
            crossAxisSpacing = 8.dp,
        ) {
            categories.forEach {
                VocabularyChip(
                    category = it,
                    isSelected = it.isDisplayedInCollection,
                    onSelectionChanged = { cat, opened ->
                        onSelectedChanged(cat, opened)
                    },
                    color = Blue,
                    selectedTextColor = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.padding(spacing.spaceExtraSmall))
    }
}