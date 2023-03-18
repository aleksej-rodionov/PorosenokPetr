package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
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

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onWordClick(word) }
            .padding(spacing.spaceMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = word.swe ?: "",
            color = Gray900,
        )

        IconButton(
            onClick = { onVoiceClick(word.swe ?: "") }
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = "Voice" //todo localized resource
            )
        }

        IconToggleButton(
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
        modifier = modifier
            .fillMaxWidth()
            .clickable { onWordClick(word) }
            .padding(spacing.spaceMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
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