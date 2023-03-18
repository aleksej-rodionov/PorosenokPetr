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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.components.VocabularyChipGroup
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.components.VocabularySearchHeader

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VocabularyMainScreen(
    owner: ComponentActivity,
    factory: ViewModelFactory
) {

    val viewModel by owner.viewModels<VocabularyViewModel> { factory }
    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    val state = viewModel.state

    BackdropScaffold(
        appBar = {
            VocabularySearchHeader(
                modifier = Modifier.background(color = MaterialTheme.colors.primary), // todo color primary
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
                categories = state.categories,
                onSelectedChanged = { cat, opened ->
                    viewModel.onEvent(VocabularyEvent.OnCategoryOpenedCHanged(cat, opened))
                }
            )
        },
        frontLayerContent = {
            //todo word list
        }
    )
}