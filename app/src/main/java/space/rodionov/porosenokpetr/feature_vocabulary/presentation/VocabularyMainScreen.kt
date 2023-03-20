package space.rodionov.porosenokpetr.feature_vocabulary.presentation

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.util.Constants.WORD_ACTIVE
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.components.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VocabularyMainScreen(
    onNavigateUp: () -> Unit,
    scaffoldState: ScaffoldState,
    owner: ComponentActivity,
    factory: ViewModelFactory
) {

    val viewModel by owner.viewModels<VocabularyViewModel> { factory }
    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    val state = viewModel.state
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
        viewModel.uiEffect.collectLatest { effect ->
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
            word = state.showDropWordProgressDialogForWord,
            onConfirmClick = {
                viewModel.onEvent(VocabularyEvent.OnWordStatusChanged(it, WORD_ACTIVE))
                viewModel.onEvent(VocabularyEvent.OnDIalogDismissed)
            },
            onDissmiss = {
                viewModel.onEvent(VocabularyEvent.OnDIalogDismissed)
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
                    viewModel.onEvent(VocabularyEvent.OnShowHideAllCategoriesSwitched(true))
                    scope.launch { sheetState.hide() }
                },
                onHideAllClick = {
                    viewModel.onEvent(VocabularyEvent.OnShowHideAllCategoriesSwitched(false))
                    scope.launch { sheetState.hide() }
                }
            )
        }
    ) {

        BackdropScaffold(
            scaffoldState = backdropState,
            appBar = {
                VocabularySearchHeader(
                    modifier = Modifier.background(color = MaterialTheme.colors.primary),
                    query = state.searchQuery,
                    shouldShowHint = state.showSearchHint,
                    onBackClick = {
                        viewModel.onEvent(VocabularyEvent.OnBackClick)
                    },
                    onMenuClick = {
                        //todo
                    },
                    onSearchTextChanged = {
                        viewModel.onEvent(VocabularyEvent.OnSearchQueryChanged(it))
                    },
                    onSearchFocusChanged = {
                        viewModel.onEvent(VocabularyEvent.OnSearchFocusChanged(it))
                    }
                )
            },
            backLayerContent = {
                VocabularyChipGroup(
                    modifier = Modifier.padding(horizontal = LocalSpacing.current.spaceMedium),
                    categories = state.categoriesInChipGroup,
                    onSelectedChanged = { cat, opened ->
                        viewModel.onEvent(VocabularyEvent.OnCategoryDisplayedChanged(cat, opened))
                    }
                )
            },
            frontLayerContent = {
                VocabularyFrontLayer(
                    modifier = Modifier,
                    items = state.frontList,
                    wordsQuantity = state.wordsQuantity,
                    onCategoryDisplayedChanged = { category, opened ->
                        viewModel.onEvent(
                            VocabularyEvent.OnCategoryDisplayedChanged(
                                category,
                                opened
                            )
                        )
                    },
                    onCategoryActiveChanged = { category, active ->
                        viewModel.onEvent(VocabularyEvent.OnCategoryActiveChanged(category, active))
                    },
                    onWordClick = { viewModel.onEvent(VocabularyEvent.OnWordClick(it)) },
                    onVoiceClick = { viewModel.onEvent(VocabularyEvent.OnVoiceClick(it)) },
                    onWordStatusChanged = { word, status ->
                        viewModel.onEvent(VocabularyEvent.OnWordStatusChanged(word, status))
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