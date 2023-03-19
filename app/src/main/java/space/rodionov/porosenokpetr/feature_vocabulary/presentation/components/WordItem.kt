package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
import space.rodionov.porosenokpetr.ui.theme.Gray200
import space.rodionov.porosenokpetr.ui.theme.Gray400
import space.rodionov.porosenokpetr.ui.theme.Gray600
import space.rodionov.porosenokpetr.ui.theme.Gray900

@Composable
fun WordItem(
    word: VocabularyItem.WordUi,
    modifier: Modifier = Modifier,
    onVoiceClick: (String) -> Unit,
    onWordActiveChanged: (VocabularyItem.WordUi, Boolean) -> Unit,
    onWordClick: (VocabularyItem.WordUi) -> Unit
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = modifier
            .background(color = Gray200)
            .fillMaxWidth()
            .clickable { onWordClick(word) }
            .padding(spacing.spaceSmall)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = word.swe ?: "",
                color = Gray900,
            )

            IconButton(
                modifier = Modifier
                    .padding(12.dp)
                    .size(24.dp, 24.dp),
                onClick = { onVoiceClick(word.swe ?: "") }
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = "Voice" //todo localized resource
                )
            }

            IconToggleButton(
                modifier = Modifier
                    .padding(12.dp)
                    .size(24.dp, 24.dp),
                checked = word.isWordActive,
                onCheckedChange = { onWordActiveChanged(word, it) }
            ) {

                Icon(
                    painter = painterResource(
                        id = if (word.isWordActive) R.drawable.ic_new_round
                        else R.drawable.ic_learned
                    ),
                    contentDescription = "Change activity"
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = spacing.spaceMedium),
                text = word.eng,
                color = Gray600,
            )

            Text(
                fontStyle = FontStyle.Italic,
                text = word.categoryName,
                color = Gray600,
                textAlign = TextAlign.End
            )
        }
    }
}