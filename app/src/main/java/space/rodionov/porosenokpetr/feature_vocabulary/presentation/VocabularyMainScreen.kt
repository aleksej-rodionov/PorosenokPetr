package space.rodionov.porosenokpetr.feature_vocabulary.presentation

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.components.VocabularyChipGroup
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.components.VocabularyFrontLayer
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.components.VocabularySearchHeader

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

    BackdropScaffold(
        scaffoldState = backdropState,
        appBar = {
            VocabularySearchHeader(
                modifier = Modifier.background(color = MaterialTheme.colors.primary),
                query = state.searchQuery,
                shouldShowHint = state.showSearchHint,
                onBackClick = {
                    //todo
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
                categories = state.categories,
                onSelectedChanged = { cat, opened ->
                    viewModel.onEvent(VocabularyEvent.OnCategoryOpenedCHanged(cat, opened))
                }
            )
        },
        frontLayerContent = {
            VocabularyFrontLayer(
                modifier = Modifier,
                items = state.categories,
                onCategoryOpenedChanged = { category, opened ->
                    viewModel.onEvent(VocabularyEvent.OnCategoryOpenedCHanged(category, opened))
                },
                onCategoryActiveChanged = { category, active ->
                    viewModel.onEvent(VocabularyEvent.OnCategoryOpenedCHanged(category, active))
                },
                onWordClick = { viewModel.onEvent(VocabularyEvent.OnWordClick(it)) },
                onVoiceClick = { viewModel.onEvent(VocabularyEvent.OnVoiceClick(it)) },
                onWordActiveChanged = { word, active ->
                    viewModel.onEvent(VocabularyEvent.OnWordActiveChanged(word, active))
                }
            )
        },
        peekHeight = 100.dp
    )
}