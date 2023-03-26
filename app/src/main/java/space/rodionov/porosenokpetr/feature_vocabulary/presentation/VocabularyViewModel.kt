package space.rodionov.porosenokpetr.feature_vocabulary.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.domain.use_case.SharedUseCases
import space.rodionov.porosenokpetr.core.util.Constants.DEFAULT_INT
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case.VocabularyUseCases
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.ext.mapCategoriesOnDisplayedChanged
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.ext.transformData
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper.toWord
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
import javax.inject.Inject

private const val TAG = "VocabularyViewModel"

@OptIn(ExperimentalCoroutinesApi::class)
class VocabularyViewModel @Inject constructor(
    private val sharedUseCases: SharedUseCases,
    private val vocabularyUseCases: VocabularyUseCases
) : ViewModel() {

    var state by mutableStateOf(VocabularyState())
        private set

    private val _uiEffect = Channel<UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private val _searchQueries = MutableStateFlow("")
    private val searchQueries: StateFlow<String> = _searchQueries.asStateFlow()

    private val _categoriesDisplayed = MutableStateFlow<List<String>>(emptyList())
    private val categoriesDisplayed: StateFlow<List<String>> = _categoriesDisplayed.asStateFlow()

    private val categoryLists = sharedUseCases.observeAllCategoriesUseCase.invoke()

    private val wordLists = combine(
        searchQueries,
        categoriesDisplayed
    ) { query, categories ->
        Pair(query, categories)
    }.flatMapLatest { (q, c) ->
        vocabularyUseCases.observeWordsBySearchQueryInCategories(q, c)
    }

    init {

        combine(
            categoryLists,
            wordLists
        ) { c, w ->
            Pair(c, w)
        }.onEach { (categories, words) ->

//            state = state.copy(isLoading = true)

            val categoriesWithWords = Pair(categories, words).transformData()
            val totalWords = words.size

            state = state.copy(
                categoriesWithWords = categoriesWithWords,
                wordsQuantity = totalWords
            )

//            state = state.copy(isLoading = false)

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
                onSearchQueryChanged(event.query)
            }
            is VocabularyEvent.OnCategoryDisplayedChanged -> {
                onCategoriesDisplayedChanged(
                    event.category,
                    event.display
                )
            }
            is VocabularyEvent.OnCategoryActiveChanged -> {
                viewModelScope.launch {
                    sharedUseCases.makeCategoryActiveUseCase.invoke(
                        event.category.name,
                        event.active
                    )
                }
            }
            is VocabularyEvent.OnWordClick -> {
                //todo open word editor
            }
            is VocabularyEvent.OnVoiceClick -> {
                sharedUseCases.speakWord.invoke(event.text)
            }
            is VocabularyEvent.OnWordStatusChanged -> {
                if (event.status == DEFAULT_INT) {
                    state = state.copy(showDropWordProgressDialogForWord = event.word)
                } else {
                    viewModelScope.launch {
                        sharedUseCases.updateWordStatusUseCase.invoke(
                            event.word.toWord(),
                            event.status
                        )
                        sharedUseCases.updateLearnedPercentInCategory.invoke(event.word.categoryName)
                    }
                }
            }
            VocabularyEvent.OnDIalogDismissed -> {
                state = state.copy(showDropWordProgressDialogForWord = null)
            }
            is VocabularyEvent.OnShowHideAllCategoriesSwitched -> {
                onShowHideAllCategoriesClick(event.show)
            }
        }
    }

    private var searchJob: Job? = null
    private fun onSearchQueryChanged(query: String) {
        state = state.copy(searchQuery = query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(700L)
            if (query.isNotBlank()) _categoriesDisplayed.value = state.categoriesWithWords.map {
                it.name
            }
            _searchQueries.value = query
        }
    }

    private var categoriesDisplayedJob: Job? = null
    private fun onCategoriesDisplayedChanged(
        category: VocabularyItem.CategoryUi,
        display: Boolean
    ) {
        val updatedCategories = state.categoriesWithWords.mapCategoriesOnDisplayedChanged(
            category, display
        )
//        state = state.copy(categoriesWithWords = updatedCategories)
        categoriesDisplayedJob?.cancel()
        categoriesDisplayedJob = viewModelScope.launch {
            delay(200L)
            _categoriesDisplayed.value = updatedCategories.filter {
                it.isDisplayedInCollection
            }.map { it.name }
        }
    }

    private fun onShowHideAllCategoriesClick(show: Boolean) {
        val updatedCategories = state.categoriesWithWords.map {
            it.copy(isDisplayedInCollection = show)
        }
//        state = state.copy(categoriesWithWords = updatedCategories)
        _categoriesDisplayed.value = updatedCategories.filter {
            it.isDisplayedInCollection
        }.map { it.name }
    }
}


data class VocabularyState(
    val categoriesWithWords: List<VocabularyItem.CategoryUi> = emptyList(),
    val wordsQuantity: Int = 0,
    val searchQuery: String = "",
    val showSearchHint: Boolean = false,
    val showDropWordProgressDialogForWord: VocabularyItem.WordUi? = null,
//    val isLoading: Boolean = false
)

sealed class VocabularyEvent {

    object OnBackClick : VocabularyEvent()
    data class OnSearchQueryChanged(val query: String) : VocabularyEvent()
    data class OnSearchFocusChanged(val isFocused: Boolean) : VocabularyEvent()
    data class OnCategoryDisplayedChanged(
        val category: VocabularyItem.CategoryUi,
        val display: Boolean
    ) : VocabularyEvent()

    data class OnCategoryActiveChanged(
        val category: VocabularyItem.CategoryUi,
        val active: Boolean
    ) : VocabularyEvent()

    data class OnWordClick(val word: VocabularyItem.WordUi) : VocabularyEvent()
    data class OnVoiceClick(val text: String) : VocabularyEvent()
    data class OnWordStatusChanged(
        val word: VocabularyItem.WordUi,
        val status: Int
    ) : VocabularyEvent()

    object OnDIalogDismissed : VocabularyEvent()
    data class OnShowHideAllCategoriesSwitched(val show: Boolean) : VocabularyEvent()
}