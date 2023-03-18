package space.rodionov.porosenokpetr.feature_vocabulary.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.use_case.SharedUseCases
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.ext.mapCategoriesOnOpenedChanged
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper.toCategoryUi
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.CategoryUi
import javax.inject.Inject

class VocabularyViewModel @Inject constructor(
    private val sharedUseCases: SharedUseCases
): ViewModel() {

    var state by mutableStateOf(VocabularyState())
        private set

    init {

        sharedUseCases.observeAllCategoriesUseCase.invoke().onEach { list ->
            val categories = list.map { it.toCategoryUi() }
            state = state.copy(categories = categories)
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: VocabularyEvent) {
        when (event) {
            is VocabularyEvent.OnSearchFocusChanged -> {
                state = state.copy(
                    showSearchHint = !event.isFocused && state.searchQuery.isBlank()
                )
            }
            is VocabularyEvent.OnSearchQueryChanged -> {
                state = state.copy(
                    searchQuery = event.query
                )
            }
            is VocabularyEvent.OnCategoryOpenedCHanged -> {
                state = state.copy(
                    categories = state.categories.mapCategoriesOnOpenedChanged(event.category, event.opened)
                )
            }
        }
    }
}



data class VocabularyState(
    val list: List<Word> = emptyList(),
    val categories: List<CategoryUi> = emptyList(),
    val searchQuery: String = "",
    val showSearchHint: Boolean = false
)

sealed class VocabularyEvent {

    data class OnSearchQueryChanged(val query: String): VocabularyEvent()
    data class OnSearchFocusChanged(val isFocused: Boolean): VocabularyEvent()
    data class OnCategoryOpenedCHanged(
        val category: CategoryUi,
        val opened: Boolean
    ): VocabularyEvent()
}