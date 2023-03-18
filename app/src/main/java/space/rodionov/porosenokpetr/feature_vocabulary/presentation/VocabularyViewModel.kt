package space.rodionov.porosenokpetr.feature_vocabulary.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.use_case.SharedUseCases
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case.VocabularyUseCases
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.ext.mapCategoriesOnOpenedChanged
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper.toCategory
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper.toCategoryUi
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
import javax.inject.Inject

class VocabularyViewModel @Inject constructor(
    private val sharedUseCases: SharedUseCases,
    private val vocabularyUseCases: VocabularyUseCases
): ViewModel() {

    var state by mutableStateOf(VocabularyState())
        private set

    private val _uiEffect = Channel<UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()



    init {

        sharedUseCases.observeAllCategoriesUseCase.invoke().onEach { list ->
            val categories = list.map { it.toCategoryUi() }
            state = state.copy(categories = categories)
        }.launchIn(viewModelScope)

        vocabularyUseCases.observeWordsBySearchQueryInCategories.invoke(
            "",
            state.categories.map { it.toCategory() }
        ).onEach {
            state = state.copy(list = it)
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: VocabularyEvent) {
        when (event) {
            is VocabularyEvent.OnBackClick -> {
                viewModelScope.launch { _uiEffect.send(UiEffect.NavigateUp) }
            }
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
            is VocabularyEvent.OnCategoryActiveChanged -> {
                //todo change in DB
            }
            is VocabularyEvent.OnWordClick -> {
                //todo open word editor
            }
            is VocabularyEvent.OnVoiceClick -> {
                //todo voice
            }
            is VocabularyEvent.OnWordActiveChanged -> {
                //todo change in DB
            }
        }
    }
}



data class VocabularyState(
    val list: List<Word> = emptyList(),
    val categories: List<VocabularyItem.CategoryUi> = emptyList(),
    val searchQuery: String = "",
    val showSearchHint: Boolean = false
)

sealed class VocabularyEvent {

    object OnBackClick: VocabularyEvent()
    data class OnSearchQueryChanged(val query: String): VocabularyEvent()
    data class OnSearchFocusChanged(val isFocused: Boolean): VocabularyEvent()
    data class OnCategoryOpenedCHanged(
        val category: VocabularyItem.CategoryUi,
        val opened: Boolean
    ): VocabularyEvent()
    data class OnCategoryActiveChanged(
        val category: VocabularyItem.CategoryUi,
        val active: Boolean
    ): VocabularyEvent()
    data class OnWordClick(val word: VocabularyItem.WordUi): VocabularyEvent()
    data class OnVoiceClick(val text: String): VocabularyEvent()
    data class OnWordActiveChanged(
        val word: VocabularyItem.WordUi,
        val active: Boolean
    ): VocabularyEvent()
}