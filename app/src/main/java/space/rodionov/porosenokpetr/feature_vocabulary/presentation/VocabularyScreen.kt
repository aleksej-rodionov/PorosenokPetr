package space.rodionov.porosenokpetr.feature_vocabulary.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
    onNavigateTo: (String) -> Unit,
    onNavigateUp: () -> Unit,
    scaffoldState: ScaffoldState,
    state: VocabularyState,
    uiEffect: Flow<UiEffect>,
    onEvent: (VocabularyEvent) -> Unit
) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)

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

                is UiEffect.NavigateTo -> {
                    onNavigateTo(effect.route)
                }
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
                }
            ) {
                onEvent(VocabularyEvent.OnShowHideAllCategoriesSwitched(false))
                scope.launch { sheetState.hide() }
            }
        }
    ) {

        BackdropScaffold(
            modifier = Modifier.background(color = MaterialTheme.colors.background),
            scaffoldState = backdropState,
            appBar = {
                VocabularySearchHeader(
                    modifier = Modifier.background(color = MaterialTheme.colors.background),
                    query = state.searchQuery,
                    shouldShowHint = state.showSearchHint,
                    onBackClick = {
                        onEvent(VocabularyEvent.OnBackClick)
                    },
                    onMenuClick = {},
                    onSearchTextChanged = {
                        onEvent(VocabularyEvent.OnSearchQueryChanged(it))
                    },
                    onSearchFocusChanged = {
                        onEvent(VocabularyEvent.OnSearchFocusChanged(it))
                    },
                    onClearQueryClick = {
                        focusManager.clearFocus()
                        onEvent(VocabularyEvent.OnSearchQueryChanged(""))
                    }
                )
            },
            backLayerContent = {
                VocabularyChipGroup(
                    modifier = Modifier
                        .padding(horizontal = LocalSpacing.current.spaceMedium)
                        .background(color = MaterialTheme.colors.background),
                    categories = state.categoriesWithWords,
                    onSelectedChanged = { cat, opened ->
                        focusManager.clearFocus()
                        onEvent(
                            VocabularyEvent.OnCategoryActiveChanged(
                                cat,
                                opened
                            )
                        ) //todo here change to OnCatActive?
                    }
                )
            },
            frontLayerContent = {
                VocabularyFrontLayer(
                    categoriesWithWords = state.categoriesWithWords,
                    wordsDisplayed = state.wordsDisplayed,
                    wordsTotal = state.wordsTotal,
                    onCategoryDisplayedChanged = { category, opened ->
                        focusManager.clearFocus()
                        onEvent(
                            VocabularyEvent.OnCategoryExpandedChanged(
                                category,
                                opened
                            )
                        )
                    },
                    onCategoryActiveChanged = { category, active ->
                        focusManager.clearFocus()
                        onEvent(VocabularyEvent.OnCategoryActiveChanged(category, active))
                    },
                    onWordClick = {
                        focusManager.clearFocus()
                        onEvent(VocabularyEvent.OnWordClick(it))
                    },
                    onVoiceClick = {
                        focusManager.clearFocus()
                        onEvent(VocabularyEvent.OnVoiceClick(it))
                    },
                    onWordStatusChanged = { word, status ->
                        focusManager.clearFocus()
                        onEvent(VocabularyEvent.OnWordStatusChanged(word, status))
                    },
                    onFilterClick = {
                        focusManager.clearFocus()
                        scope.launch { sheetState.show() }
                    },
                    onFocusedCategoryChanged = {
                        onEvent(VocabularyEvent.OnFocusedCategoryChanged(it))
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
        onNavigateTo = {},
        onNavigateUp = {},
        scaffoldState = scaffoldState,
        state = VocabularyState(),
        uiEffect = emptyFlow(),
        onEvent = {}
    )
}