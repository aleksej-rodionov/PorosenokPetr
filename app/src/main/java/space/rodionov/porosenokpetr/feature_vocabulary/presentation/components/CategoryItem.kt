package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import space.rodionov.porosenokpetr.core.presentation.LocalNativeLanguage
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
import space.rodionov.porosenokpetr.ui.theme.Gray600

@Composable
fun CategoryItem(
    category: VocabularyItem.CategoryUi,
    modifier: Modifier = Modifier,
    onCategoryDisplayedChanged: (VocabularyItem.CategoryUi, Boolean) -> Unit,
    onCategoryActiveChanged: (VocabularyItem.CategoryUi, Boolean) -> Unit
) {

    val spacing = LocalSpacing.current
    val nativeLanguage = LocalNativeLanguage.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCategoryDisplayedChanged(category, !category.isExpanded)
            }
            .background(color = MaterialTheme.colors.surface)
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = category.getLocalizedName(nativeLanguage.current),
                color = MaterialTheme.colors.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.padding(end = 12.dp),
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp,
                text = "${category.learnedFromActivePercentage}%",
                color = Gray600,
                textAlign = TextAlign.End
            )

            Checkbox(
                checked = category.isCategoryActive,
                onCheckedChange = {
                    onCategoryActiveChanged(category, it)
                },
                colors = CheckboxDefaults.colors(checkmarkColor = Color.White)
            )

            IconToggleButton(
                checked = category.isExpanded,
                onCheckedChange = {
                    onCategoryDisplayedChanged(category, it)
                }
            ) {

                Icon(
                    imageVector = if (category.isExpanded) Icons.Filled.KeyboardArrowDown
                    else Icons.Default.KeyboardArrowUp,
                    contentDescription = "Open/close"
                )
            }
        }
    }
}