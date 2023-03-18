package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.CategoryUi

@Composable
fun VocabularyChip(
    category: CategoryUi,
    isSelected: Boolean = false,
    onSelectionChanged: (CategoryUi, Boolean) -> Unit,
) {

    Surface(
        modifier = Modifier.padding(14.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(category, it)
                }
            )
        ) {
            Text(
                text = category.getLocalizedName(2), //todo compositionLocal for nativeLanguage
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}




