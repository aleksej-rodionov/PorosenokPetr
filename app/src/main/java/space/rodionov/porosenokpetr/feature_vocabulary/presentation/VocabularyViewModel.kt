package space.rodionov.porosenokpetr.feature_vocabulary.presentation

import android.util.Log
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
import space.rodionov.porosenokpetr.core.domain.use_case.MakeCategoryActiveUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SpeakWordUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateLearnedPercentInCategoryUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateWordUseCase
import space.rodionov.porosenokpetr.core.util.Constants.DEFAULT_INT
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.ObserveAllCategoriesUseCase
import space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case.ObserveWordsBySearchQueryInCategories
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.ext.mapCategoriesOnExpandedChanged
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.ext.transformData
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper.toWord
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
import space.rodionov.porosenokpetr.main.navigation.sub_graphs.VocabularyDestinations

@OptIn(ExperimentalCoroutinesApi::class)
class VocabularyViewModel(
    observeAllCategoriesUseCase: ObserveAllCategoriesUseCase,
    private val observeWordsBySearchQueryInCategories: ObserveWordsBySearchQueryInCategories,
    private val makeCategoryActiveUseCase: MakeCategoryActiveUseCase,
    private val speakWordUseCase: SpeakWordUseCase,
    private val updateWordUseCase: UpdateWordUseCase,
    private val updateLearnedPercentInCategoryUseCase: UpdateLearnedPercentInCategoryUseCase
) : ViewModel() {

    var state by mutableStateOf(VocabularyState())
        private set

    private val _uiEffect = Channel<UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private val _searchQueries = MutableStateFlow("")
    private val searchQueries: StateFlow<String> = _searchQueries.asStateFlow()

    private val _categoriesExpanded = MutableStateFlow<List<String>>(emptyList())
    private val categoriesExpanded: StateFlow<List<String>> = _categoriesExpanded.asStateFlow()

    private val categoryLists = observeAllCategoriesUseCase.invoke()

    private val wordLists = combine(
        searchQueries,
        categoriesExpanded
    ) { query, categories ->
        Pair(query, categories)
    }.flatMapLatest { (q, c) ->
        observeWordsBySearchQueryInCategories(q, c)
    }

    init {
        combine(
            categoryLists,
            wordLists
        ) { c, w ->
            Pair(c, w)
        }.onEach { (categories, words) ->

            val categoriesWithWords = Pair(categories, words).transformData()
            val totalWords = words.size

            state = state.copy(
                categoriesWithWords = categoriesWithWords,
                wordsQuantity = totalWords
            )

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

            is VocabularyEvent.OnCategoryExpandedChanged -> {
                onCategoriesExpandedChanged(
                    event.category,
                    event.expand
                )
            }

            is VocabularyEvent.OnCategoryActiveChanged -> {
                viewModelScope.launch {
                    makeCategoryActiveUseCase.invoke(
                        event.category.name,
                        event.active
                    )
                }
            }

            is VocabularyEvent.OnWordClick -> {
                viewModelScope.launch {
                    _uiEffect.send(
                        UiEffect.NavigateTo(
                            "${VocabularyDestinations.WordEditor.route}/${event.word.id}"
                        )
                    )
                }
            }

            is VocabularyEvent.OnVoiceClick -> {
                speakWordUseCase.invoke(event.text)
            }

            is VocabularyEvent.OnWordStatusChanged -> {
                if (event.status == DEFAULT_INT) {
                    state = state.copy(showDropWordProgressDialogForWord = event.word)
                } else {
                    viewModelScope.launch {
                        updateWordUseCase.invoke(
                            event.word.copy(wordStatus = event.status).toWord()
                        )
                        updateLearnedPercentInCategoryUseCase.invoke(event.word.categoryName)
                    }
                }
            }

            VocabularyEvent.OnDialogDismissed -> {
                state = state.copy(showDropWordProgressDialogForWord = null)
            }

            is VocabularyEvent.OnShowHideAllCategoriesSwitched -> {
                onShowHideAllCategoriesClick(event.show)
            }

            is VocabularyEvent.OnFocusedCategoryChanged -> {
                Log.d("LAZY_SHIT", "onEvent: focused cat = ${event.category.nameRus}")
                state = state.copy(categoriesWithWords = state.categoriesWithWords.map {
                    if (it == event.category) {
                        it.copy(isFocusedInList = true)
                    } else {
                        it.copy(isFocusedInList = false)
                    }
                })
            }
        }
    }

    private var searchJob: Job? = null
    private fun onSearchQueryChanged(query: String) {
        state = state.copy(searchQuery = query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(700L)
            if (query.isNotBlank()) _categoriesExpanded.value = state.categoriesWithWords.map {
                it.name
            }
            _searchQueries.value = query
        }
    }

    private var categoriesDisplayedJob: Job? = null
    private fun onCategoriesExpandedChanged(
        category: VocabularyItem.CategoryUi,
        display: Boolean
    ) {
        val updatedCategories = state.categoriesWithWords.mapCategoriesOnExpandedChanged(
            category, display
        )
        categoriesDisplayedJob?.cancel()
        categoriesDisplayedJob = viewModelScope.launch {
            delay(200L)
            _categoriesExpanded.value = updatedCategories.filter {
                it.isExpanded
            }.map { it.name }
        }
    }

    private fun onShowHideAllCategoriesClick(show: Boolean) {
        val updatedCategories = state.categoriesWithWords.map {
            it.copy(isExpanded = show)
        }
        _categoriesExpanded.value = updatedCategories.filter {
            it.isExpanded
        }.map { it.name }
    }
}


data class VocabularyState(
    val categoriesWithWords: List<VocabularyItem.CategoryUi> = emptyList(),
    val wordsQuantity: Int = 0,
    val searchQuery: String = "",
    val showSearchHint: Boolean = false,
    val showDropWordProgressDialogForWord: VocabularyItem.WordUi? = null
)

sealed class VocabularyEvent {

    object OnBackClick : VocabularyEvent()
    data class OnSearchQueryChanged(val query: String) : VocabularyEvent()
    data class OnSearchFocusChanged(val isFocused: Boolean) : VocabularyEvent()
    data class OnCategoryExpandedChanged(
        val category: VocabularyItem.CategoryUi,
        val expand: Boolean
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

    object OnDialogDismissed : VocabularyEvent()
    data class OnShowHideAllCategoriesSwitched(val show: Boolean) : VocabularyEvent()
    data class OnFocusedCategoryChanged(val category: VocabularyItem.CategoryUi) : VocabularyEvent()
}