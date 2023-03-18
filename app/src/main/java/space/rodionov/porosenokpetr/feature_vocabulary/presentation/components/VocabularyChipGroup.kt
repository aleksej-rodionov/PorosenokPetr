package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.CategoryUi

@Composable
fun VocabularyChipGroup(
    modifier: Modifier = Modifier,
    categories: List<CategoryUi> = emptyList(),
    onSelectedChanged: (CategoryUi, Boolean) -> Unit
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier.padding(spacing.spaceSmall)
    ) {

        FlowRow(
            mainAxisSpacing = 16.dp,
            crossAxisSpacing = 16.dp,
        ) {
            categories.forEach {
                VocabularyChip(
                    category = it,
                    isSelected = it.isOpenedInCollection,
                    onSelectionChanged = { cat, opened ->
                        onSelectedChanged(cat, opened)
                    }
                )
            }
        }
    }
}