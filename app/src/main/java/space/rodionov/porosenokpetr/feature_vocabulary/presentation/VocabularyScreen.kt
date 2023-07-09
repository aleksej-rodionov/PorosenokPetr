package space.rodionov.porosenokpetr.feature_vocabulary.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.util.Constants.WORD_ACTIVE
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.components.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VocabularyScreen(
    onNavigateUp: () -> Unit,
    scaffoldState: ScaffoldState,
    state: VocabularyState,
    uiEffect: Flow<UiEffect>,
    onEvent: (VocabularyEvent) -> Unit
) {

    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val scope = rememberCoroutineScope()
    BackHandler(sheetState.isVisible) {
        scope.launch { sheetState.hide() }
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

    if (state.showDropWordProgressDialogForWord != null) {
        DropWordProgressDialog(
            wordToAsk = state.showDropWordProgressDialogForWord,
            onConfirmClick = {
                onEvent(VocabularyEvent.OnWordStatusChanged(it, WORD_ACTIVE))
                onEvent(VocabularyEvent.OnDialogDismissed)
            },
            onDissmiss = {
                onEvent(VocabularyEvent.OnDialogDismissed)
            }
        )
    }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),
        sheetContent = {

            CategoryFilterBottomDrawer(
                onDisplayAllClick = {
                    onEvent(VocabularyEvent.OnShowHideAllCategoriesSwitched(true))
                    scope.launch { sheetState.hide() }
                },
                onHideAllClick = {
                    onEvent(VocabularyEvent.OnShowHideAllCategoriesSwitched(false))
                    scope.launch { sheetState.hide() }
                }
            )
        }
    ) {

        BackdropScaffold(
            modifier = Modifier.background(color = MaterialTheme.colors.background), //todo was Gray300
            scaffoldState = backdropState,
            appBar = {
                VocabularySearchHeader(
                    modifier = Modifier.background(color = MaterialTheme.colors.background), //todo was Gray300
                    query = state.searchQuery,
                    shouldShowHint = state.showSearchHint,
                    onBackClick = {
                        onEvent(VocabularyEvent.OnBackClick)
                    },
                    onMenuClick = {
                        //todo
                    },
                    onSearchTextChanged = {
                        onEvent(VocabularyEvent.OnSearchQueryChanged(it))
                    },
                    onSearchFocusChanged = {
                        onEvent(VocabularyEvent.OnSearchFocusChanged(it))
                    }
                )
            },
            backLayerContent = {
                VocabularyChipGroup(
                    modifier = Modifier
                        .padding(horizontal = LocalSpacing.current.spaceMedium)
                        .background(color = MaterialTheme.colors.background), //todo was Gray300
                    categories = state.categoriesWithWords,
                    onSelectedChanged = { cat, opened ->
                        onEvent(VocabularyEvent.OnCategoryDisplayedChanged(cat, opened))
                    }
                )
            },
            frontLayerContent = {
                VocabularyFrontLayer(
                    modifier = Modifier,
                    categoriesWithWords = state.categoriesWithWords,
                    wordsQuantity = state.wordsQuantity,
                    onCategoryDisplayedChanged = { category, opened ->
                        onEvent(
                            VocabularyEvent.OnCategoryDisplayedChanged(
                                category,
                                opened
                            )
                        )
                    },
                    onCategoryActiveChanged = { category, active ->
                        onEvent(VocabularyEvent.OnCategoryActiveChanged(category, active))
                    },
                    onWordClick = { onEvent(VocabularyEvent.OnWordClick(it)) },
                    onVoiceClick = { onEvent(VocabularyEvent.OnVoiceClick(it)) },
                    onWordStatusChanged = { word, status ->
                        onEvent(VocabularyEvent.OnWordStatusChanged(word, status))
                    },
                    onFilterClick = {
                        scope.launch { sheetState.show() }
                    }
                )
            },
            peekHeight = 100.dp
        )
    }
}

@Preview
@Composable
fun VocabularyScreenPreview(
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    VocabularyScreen(
        onNavigateUp = {},
        scaffoldState = scaffoldState,
        state = VocabularyState(),
        uiEffect = emptyFlow(),
        onEvent = {}
    )
}