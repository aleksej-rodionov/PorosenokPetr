package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
import space.rodionov.porosenokpetr.ui.theme.Gray600
import space.rodionov.porosenokpetr.ui.theme.Gray900
import space.rodionov.porosenokpetr.ui.theme.TransparentGray15

@Composable
fun VocabularyChip(
    modifier: Modifier = Modifier,
    category: VocabularyItem.CategoryUi,
    isSelected: Boolean = false,
    isFocusedInList: Boolean = false,
    onSelectionChanged: (VocabularyItem.CategoryUi, Boolean) -> Unit,
    color: Color,
    selectedTextColor: Color,
    textStyle: TextStyle = MaterialTheme.typography.button
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .border(
                width = 2.dp,
                color = if (isFocusedInList) MaterialTheme.colors.onBackground else {
                    if (isSelected) color else TransparentGray15
                },
                shape = RoundedCornerShape(100.dp)
            )
            .background(
                color = if (isSelected) color else TransparentGray15,
                shape = RoundedCornerShape(100.dp)
            )
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(category, it)
                }
            )
            .padding(
                horizontal = LocalSpacing.current.spaceSmall,
                vertical = 6.dp
            )
    ) {

        Text(
            text = category.getLocalizedName(2), //todo compositionLocal for nativeLanguage
            style = textStyle,
            color = if (isSelected) selectedTextColor else Gray600, //todo this doesn't get repainted =(
        )
    }
}




