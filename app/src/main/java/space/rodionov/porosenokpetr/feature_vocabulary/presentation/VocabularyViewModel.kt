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
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.use_case.SharedUseCases
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case.VocabularyUseCases
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.ext.mapCategoriesOnDisplayedChanged
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper.toCategoryUi
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper.toWordUi
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.testCategory
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.testWord
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

    init {

        // this is only for categories' chipGroup
        viewModelScope.launch {
            val allCategories = sharedUseCases.getAllCategoriesUseCase.invoke().map {
                it.toCategoryUi().copy(
                    isDisplayedInCollection = categoriesDisplayed.value.contains(it.resourceName)
                )
            }
            state = state.copy(categories = allCategories)
        }

        combine(
            searchQueries,
            categoriesDisplayed
        ) { query, categories ->
            Pair(query, categories)
        }.flatMapLatest { (q, c) ->
            vocabularyUseCases.observeWordsBySearchQueryInCategories(q, c)
        }.onEach { words ->
            // todo handle result
            val wordsQuantity = 2
//            Log.d(TAG, "wordList.size = ${words.size}")
//            state = state.copy(frontList = words.map { it.toWordUi() })

            val testItems = listOf<VocabularyItem>(
                testWord.toWordUi(),
                testCategory.toCategoryUi()
            )
            state = state.copy(
                frontList = testItems,
                wordsQuantity = wordsQuantity
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
            is VocabularyEvent.OnCategoryDisplayedCHanged -> {
                onCategoriesDisplayedChanged(
                    event.category,
                    event.display
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

    private var searchJob: Job? = null
    private fun onSearchQueryChanged(query: String) {
        state = state.copy(searchQuery = query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(700L)
            _searchQueries.value = query
        }
    }

    private var categoriesDisplayedJob: Job? = null
    private fun onCategoriesDisplayedChanged(
        category: VocabularyItem.CategoryUi,
        display: Boolean
    ) {
        val updatedCategories = state.categories.mapCategoriesOnDisplayedChanged(
            category, display
        )
        state = state.copy(categories = updatedCategories)
        categoriesDisplayedJob?.cancel()
        categoriesDisplayedJob = viewModelScope.launch {
            delay(700L)
            _categoriesDisplayed.value = updatedCategories.filter {
                it.isDisplayedInCollection
            }.map { it.resourceName }
        }
    }
}


data class VocabularyState(
    val frontList: List<VocabularyItem> = emptyList(),
    val wordsQuantity: Int = 0,
    val categories: List<VocabularyItem.CategoryUi> = emptyList(),
    val searchQuery: String = "",
    val showSearchHint: Boolean = false
)

sealed class VocabularyEvent {

    object OnBackClick : VocabularyEvent()
    data class OnSearchQueryChanged(val query: String) : VocabularyEvent()
    data class OnSearchFocusChanged(val isFocused: Boolean) : VocabularyEvent()
    data class OnCategoryDisplayedCHanged(
        val category: VocabularyItem.CategoryUi,
        val display: Boolean
    ) : VocabularyEvent()

    data class OnCategoryActiveChanged(
        val category: VocabularyItem.CategoryUi,
        val active: Boolean
    ) : VocabularyEvent()

    data class OnWordClick(val word: VocabularyItem.WordUi) : VocabularyEvent()
    data class OnVoiceClick(val text: String) : VocabularyEvent()
    data class OnWordActiveChanged(
        val word: VocabularyItem.WordUi,
        val active: Boolean
    ) : VocabularyEvent()
}