package space.rodionov.porosenokpetr.feature_settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.core.presentation.components.PlainItem
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.components.TopLine
import space.rodionov.porosenokpetr.ui.theme.Gray600

@Composable
fun SettingsBottomDrawer(
    modifier: Modifier = Modifier,
    languages: List<Language>,
    onLanguageClick: (Language) -> Unit
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.spaceMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
                    .width(80.dp),
                color = Gray600
            )
        }
        Divider()

        LazyColumn(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {

            itemsIndexed(languages) { index, language ->

                    PlainItem(
                        text = stringResource(id = language.languageNameRes),
                        onClick = { onLanguageClick(language) }
                    )

                if (index < languages.size - 1) {
                    Divider()
                }
            }
        }
    }
}