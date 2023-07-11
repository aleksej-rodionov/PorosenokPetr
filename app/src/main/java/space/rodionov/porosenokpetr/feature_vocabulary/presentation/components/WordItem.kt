package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalNativeLanguage
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.util.Constants.DEFAULT_INT
import space.rodionov.porosenokpetr.core.util.Constants.WORD_ACTIVE
import space.rodionov.porosenokpetr.core.util.Constants.WORD_EXCLUDED
import space.rodionov.porosenokpetr.core.util.Constants.WORD_LEARNED
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
import space.rodionov.porosenokpetr.ui.theme.*

@Composable
fun WordItem(
    word: VocabularyItem.WordUi,
    modifier: Modifier = Modifier,
    onVoiceClick: (String) -> Unit,
    onWordStatusChanged: (VocabularyItem.WordUi, Int) -> Unit,
    onWordClick: (VocabularyItem.WordUi) -> Unit
) {

    val spacing = LocalSpacing.current
    val nativeLanguage = LocalNativeLanguage.current

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxWidth()
            .clickable { onWordClick(word) }
            .padding(horizontal = spacing.spaceMedium)
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
                color = MaterialTheme.colors.onBackground,
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

            IconButton(
                onClick = {
                    onWordStatusChanged(
                        word,
                        when (word.wordStatus) {
                            WORD_ACTIVE -> WORD_EXCLUDED
                            WORD_EXCLUDED -> WORD_ACTIVE
                            else -> DEFAULT_INT
                        }
                    )
                }
            ) {

                Icon(
                    painter = painterResource(
                        id = when (word.wordStatus) {
                            WORD_EXCLUDED -> R.drawable.ic_baseline_close_24
                            WORD_ACTIVE -> R.drawable.ic_baseline_insights_24
                            WORD_LEARNED -> R.drawable.ic_baseline_done_24
                            else -> R.drawable.ic_baseline_live_help_24
                        }
                    ),
                    tint = when (word.wordStatus) {
                        WORD_EXCLUDED -> Red
                        WORD_ACTIVE -> Blue
                        WORD_LEARNED -> JuicyGreen
                        else -> Gray600
                    },
                    contentDescription = "Change activity"
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = spacing.spaceSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = word.getTranslation(nativeLanguage.current),
                color = Gray600,
            )

            Text(
                modifier = Modifier.padding(end = 12.dp),
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp,
                text = word.categoryName,
                color = Gray600,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview
@Composable
fun WordItemPreview() {
    WordItem(
        word = VocabularyItem.WordUi("xuj", "xuj", "xuj", "xuj", "xuj"),
        onVoiceClick = {},
        onWordStatusChanged = { _, _ -> },
        onWordClick = {}
    )
}