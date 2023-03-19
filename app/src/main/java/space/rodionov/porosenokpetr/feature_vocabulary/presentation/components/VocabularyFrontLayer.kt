package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
import space.rodionov.porosenokpetr.ui.theme.Gray600

@Composable
fun VocabularyFrontLayer(
    modifier: Modifier = Modifier,
    items: List<VocabularyItem>,
    wordsQuantity: Int,
    onCategoryDisplayedChanged: (VocabularyItem.CategoryUi, Boolean) -> Unit,
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

        Column(
            modifier = Modifier.fillMaxWidth()
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
                    .padding(bottom = 12.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Всего отображено $wordsQuantity слов",
                        fontStyle = FontStyle.Italic,
                        fontSize = 14.sp
                    )

                    IconButton(
                        onClick = {//todo
                             }
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter_settings),
                            contentDescription = "Rearrange"
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items) { item ->
                    when (item) {
                        is VocabularyItem.CategoryUi -> {

                            CategoryItem(
                                category = item,
                                onCategoryDisplayedChanged = { category, opened ->
                                    onCategoryDisplayedChanged(category, opened)
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

                    Divider()
                }
            }

            BottomShadow()
        }
    }
}