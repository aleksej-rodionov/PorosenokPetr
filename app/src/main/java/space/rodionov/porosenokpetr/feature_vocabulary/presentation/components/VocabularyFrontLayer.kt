package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
import space.rodionov.porosenokpetr.ui.theme.Gray600

@Composable
fun VocabularyFrontLayer(
    modifier: Modifier = Modifier,
    items: List<VocabularyItem>,
    onCategoryOpenedChanged: (VocabularyItem.CategoryUi, Boolean) -> Unit,
    onCategoryActiveChanged: (VocabularyItem.CategoryUi, Boolean) -> Unit,
    onWordClick: (VocabularyItem.WordUi) -> Unit,
    onVoiceClick: (String) -> Unit,
    onWordActiveChanged: (VocabularyItem.WordUi, Boolean) -> Unit
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .height(spacing.spaceLarge)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {

            TopLine(
                modifier = Modifier
                    .height(spacing.spaceExtraSmall)
                    .width(120.dp),
                color = Gray600
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Всего отображено 120 слов",
                fontStyle = FontStyle.Italic
            )

//            IconButton(onClick = { /*TODO*/ }) {
//
//            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items) { item ->
                when (item) {
                    is VocabularyItem.CategoryUi -> {

                        CategoryItem(
                            category = item,
                            onCategoryOpenedChanged = { category, opened ->
                                onCategoryActiveChanged(category, opened)
                            },
                            onCategoryActiveChanged = { category, active ->
                                onCategoryActiveChanged(category, active)
                            }
                        )
                    }
                    is VocabularyItem.WordUi -> {

                        WordItem(
                            word = item,
                            onVoiceClick = { onVoiceClick(it) },
                            onWordActiveChanged = { word, active ->
                                onWordActiveChanged(
                                    word,
                                    active
                                )
                            },
                            onWordClick = { onWordClick(it) }
                        )
                    }
                }
            }
        }
    }
}