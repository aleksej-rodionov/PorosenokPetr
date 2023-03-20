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
import space.rodionov.porosenokpetr.core.util.Constants.WORD_ACTIVE
import space.rodionov.porosenokpetr.core.util.Constants.WORD_EXCLUDED
import space.rodionov.porosenokpetr.core.util.Constants.WORD_LEARNED
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case.VocabularyUseCases
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.ext.mapCategoriesOnDisplayedChanged
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper.toCategoryUi
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper.toWord
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.mapper.toWordUi
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

    private val _allCategories = MutableStateFlow<List<VocabularyItem.CategoryUi>>(emptyList())
    private val allCategories: StateFlow<List<VocabularyItem.CategoryUi>> =
        _allCategories.asStateFlow()

    init {

        // this is only for categories' chipGroup
        viewModelScope.launch {
            val allCats = sharedUseCases.getAllCategoriesUseCase.invoke().map {
                it.toCategoryUi().copy(
                    isDisplayedInCollection = categoriesDisplayed.value.contains(it.name)
                )
            }
            state = state.copy(categoriesInChipGroup = allCats)
        }

        viewModelScope.launch {
            sharedUseCases.observeAllCategoriesUseCase.invoke().collectLatest { categories ->

                val allCats = categories.map {
                    it.toCategoryUi()
                        .copy(isDisplayedInCollection = categoriesDisplayed.value.contains(it.name))
                }

                _allCategories.value = allCats
                val updatedFrontList = state.frontList.map { item ->
                    when (item) {
                        is VocabularyItem.CategoryUi -> {
                            item.copy(isCategoryActive = allCats.find { category ->
                                category.name == item.name
                            }?.isCategoryActive == true)
                        }
                        is VocabularyItem.WordUi -> item
                    }
                }
                state = state.copy(
                    frontList = updatedFrontList
                )
            }
        }

        combine(
            searchQueries,
            categoriesDisplayed
        ) { query, categories ->
            Pair(query, categories)
        }.flatMapLatest { (q, c) ->
            vocabularyUseCases.observeWordsBySearchQueryInCategories(q, c)
        }.onEach { words ->

            val wordsQuantity = words.size
            state = state.copy(wordsQuantity = wordsQuantity)

            val newList = mutableListOf<VocabularyItem>()
            val allCats = allCategories.value.map { category ->
                category.copy(
                    isDisplayedInCollection = categoriesDisplayed.value.contains(category.name)
                )
            }
            val wordsReceived: List<VocabularyItem.WordUi> = words.map { it.toWordUi() }

            allCats.forEach { category ->
                newList.add(category)
                newList.addAll(wordsReceived.filter { word ->
                    word.categoryName == category.name
                })
            }

            state = state.copy(frontList = newList)

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
                //todo voice
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
                        sharedUseCases.updateLearnedPercentInCategory(event.word.categoryName)
                    }
                }
            }
            VocabularyEvent.OnDIalogDismissed -> {
                state = state.copy(showDropWordProgressDialogForWord = null)
            }
            is VocabularyEvent.OnFilterClick -> {
                //todo open bottomDrawer filter
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
            _searchQueries.value = query
        }
    }

    private var categoriesDisplayedJob: Job? = null
    private fun onCategoriesDisplayedChanged(
        category: VocabularyItem.CategoryUi,
        display: Boolean
    ) {
        val updatedCategories = state.categoriesInChipGroup.mapCategoriesOnDisplayedChanged(
            category, display
        )
        state = state.copy(categoriesInChipGroup = updatedCategories)
        categoriesDisplayedJob?.cancel()
        categoriesDisplayedJob = viewModelScope.launch {
            delay(200L)
            _categoriesDisplayed.value = updatedCategories.filter {
                it.isDisplayedInCollection
            }.map { it.name }
        }
    }

    private fun onShowHideAllCategoriesClick(show: Boolean) {
        val updatedCategories = state.categoriesInChipGroup.map {
            it.copy(isDisplayedInCollection = show)
        }
        state = state.copy(categoriesInChipGroup = updatedCategories)
        _categoriesDisplayed.value = updatedCategories.filter {
            it.isDisplayedInCollection
        }.map { it.name }
    }
}


data class VocabularyState(
    val frontList: List<VocabularyItem> = emptyList(),
    val wordsQuantity: Int = 0,
    val categoriesInChipGroup: List<VocabularyItem.CategoryUi> = emptyList(),
    val searchQuery: String = "",
    val showSearchHint: Boolean = false,
    val showDropWordProgressDialogForWord: VocabularyItem.WordUi? = null
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
    object OnDIalogDismissed: VocabularyEvent()

    object OnFilterClick : VocabularyEvent()
    data class OnShowHideAllCategoriesSwitched(val show: Boolean): VocabularyEvent()
}