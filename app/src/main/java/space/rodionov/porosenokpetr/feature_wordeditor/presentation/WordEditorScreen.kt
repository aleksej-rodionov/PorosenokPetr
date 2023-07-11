package space.rodionov.porosenokpetr.feature_wordeditor.presentation

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.presentation.components.TopBar
import space.rodionov.porosenokpetr.core.util.Constants.DEFAULT_STRING
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.components.WordEditorItem
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.model.Translation

@Composable
fun WordEditorScreen(
    onNavigateUp: () -> Unit,
    args: Bundle? = null,
    scaffoldState: ScaffoldState,
    state: WordEditorState,
    uiEffect: Flow<UiEffect>,
    onEvent: (WordEditorEvent) -> Unit
) {

    val spacing = LocalSpacing.current
    val context = LocalContext.current

    if (state.wordId == null) {
        val wordId = args?.getString("name")
        onEvent(WordEditorEvent.OnReceivedWordId(wordId ?: DEFAULT_STRING))
    }

    LaunchedEffect(key1 = true) {
        uiEffect.collectLatest { effect ->
            when (effect) {
                is UiEffect.NavigateUp -> {
                    onNavigateUp()
                }

                is UiEffect.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        effect.msg.asString(context)
                    )
                }

                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {

        Divider()

        TopBar(
            text = R.string.word_editor,
            hasMenuIcon = false,
            onBackClick = { onEvent(WordEditorEvent.OnBackClick) },
            onMenuClick = {}
        )

        Divider()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            LazyColumn(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(spacing.spaceMedium)
                    )
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {

                itemsIndexed(state.translations) { index, translation ->

                    WordEditorItem(
                        translation = translation,
                        onValueChange = {
                            onEvent(
                                WordEditorEvent.OnTranslationChanged(
                                    translation.language,
                                    it
                                )
                            )
                        },
                        onDone = {
                            onEvent(WordEditorEvent.OnSaveClick)
                        }
                    )

                    if (index < state.translations.size - 1) {
                        Divider()
                    }
                }
            }

            FloatingActionButton(
                onClick = { onEvent(WordEditorEvent.OnSaveClick) },
                modifier = Modifier.align(Alignment.BottomEnd),
                backgroundColor = MaterialTheme.colors.primary
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "Save",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Preview
@Composable
fun WordEditorScreenPreview(
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    WordEditorScreen(
        onNavigateUp = {},
        scaffoldState = scaffoldState,
        state = WordEditorState(
            translations = listOf(
                Translation(Language.Russian, "Хуй"),
                Translation(Language.Ukrainian, "Хуй"),
                Translation(Language.English, "Dick"),
                Translation(Language.Swedish, "Kuk")
            )
        ),
        uiEffect = emptyFlow(),
        onEvent = {}
    )
}