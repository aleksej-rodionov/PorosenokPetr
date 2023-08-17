package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
import space.rodionov.porosenokpetr.ui.theme.Gray600
import space.rodionov.porosenokpetr.ui.theme.TransparentGray15

@Composable
fun VocabularyChip(
    modifier: Modifier = Modifier,
    category: VocabularyItem.CategoryUi,
    isExpanded: Boolean = false,
    isFocusedInList: Boolean = false, //todo change
    onSelectionChanged: (VocabularyItem.CategoryUi, Boolean) -> Unit,
    turnedOnColor: Color,
    turnedOnTextColor: Color,
    expandedDotColor: Color,
    isTurnedOn: Boolean = false, //todo change
    textStyle: TextStyle = MaterialTheme.typography.button
) {

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .border(
                width = 2.dp,
                color = if (isFocusedInList) Color.Green else {
                    if (isTurnedOn) turnedOnColor else TransparentGray15
                },
                shape = RoundedCornerShape(100.dp)
            )
            .background(
                color = if (isTurnedOn) turnedOnColor else TransparentGray15,
                shape = RoundedCornerShape(100.dp)
            )
            .toggleable(
                value = isTurnedOn,
                onValueChange = {
                    onSelectionChanged(category, it)
                }
            )
            .padding(
                horizontal = LocalSpacing.current.spaceSmall,
                vertical = 6.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(4.dp)
                .background(
                    color = if (isExpanded) expandedDotColor else Gray600,
                    shape = CircleShape
                )
        )

        Text(
            text = category.getLocalizedName(Language.English),
            modifier = Modifier.padding(start = 2.dp),
            color = if (isTurnedOn) turnedOnTextColor else Gray600,
            style = textStyle
        )
    }
}

@Preview
@Composable
fun VocabularyChipPreview() {
    VocabularyChip(
        category = VocabularyItem.CategoryUi(
            "shit",
            nameRus = "Говно",
            nameEng = "Shit",
            nameUkr = "Гiвно"
        ),
        onSelectionChanged = { _, _ -> },
        turnedOnColor = MaterialTheme.colors.primary,
        turnedOnTextColor = Color.White,
        expandedDotColor = Color.Green
    )
}