package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.Constants.EMPTY_STRING
import space.rodionov.porosenokpetr.Constants.TAG_PETR
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.CatNameFromStorageUseCase
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.ObserveWordsSearchQueryUseCase
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.UpdateCatNameStorageUseCase
import javax.inject.Inject

@HiltViewModel
class WordlistViewModel @Inject constructor(
    private val catNameFromStorageUseCase: CatNameFromStorageUseCase,
    private val updateCatNameStorageUseCase: UpdateCatNameStorageUseCase,
    private val observeWordsSearchQueryUseCase: ObserveWordsSearchQueryUseCase,
    private val state: SavedStateHandle
) : ViewModel() {
    var catToSearchIn = state.getLiveData<Category>("category", null)
    val catNameFlow = catNameFromStorageUseCase.invoke()

    val searchQuery = state.getLiveData("searchQuery", "")
    fun triggerSearchQuery(): Flow<String> = flow {
        val query = searchQuery.value ?: EMPTY_STRING
        emit(query)
    }

    private val wordsFlow = combine(
        catNameFlow,
        searchQuery.asFlow()
//        triggerSearchQuery()
    ) { catName, query ->
        Pair(catName, query)
    }.flatMapLatest { (catName, query) ->
        observeWordsSearchQueryUseCase.invoke(catName, query)
    }

    val words = wordsFlow.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private var searchJob: Job? = null
    fun onSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            searchQuery.value = query
            Log.d(TAG_PETR, "onSearch: query updated as $query")
        }
    }

    fun updateCatStorage(catName: String) = viewModelScope.launch {
        updateCatNameStorageUseCase.invoke(catName)
    }
}







