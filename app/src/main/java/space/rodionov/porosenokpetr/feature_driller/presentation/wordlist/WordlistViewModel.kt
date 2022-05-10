package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist

import android.util.Log
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_PETR
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_EN
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_RU
import javax.inject.Inject

class WordlistViewModel @Inject constructor(
    private val drillerUseCases: DrillerUseCases,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _nativeLang = drillerUseCases.observeNativeLangUseCase.invoke()
    val nativeLang = _nativeLang.stateIn(viewModelScope, SharingStarted.Lazily, LANGUAGE_RU)

    private val _learnedLang = drillerUseCases.observeLearnedLangUseCase.invoke()
    val learnedLang = _learnedLang.stateIn(viewModelScope, SharingStarted.Lazily, LANGUAGE_EN)

    var catToSearchIn = state.getLiveData<Category>("category", null)
    val catNameFlow = drillerUseCases.catNameFromStorageUseCase.invoke()

    val searchQuery = state.getLiveData("searchQuery", "")

    //==================================MODE==========================
    private val _mode = drillerUseCases.observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, MODE_LIGHT)

    //==============================UI EVENTS==============================
    private val _eventFlow = MutableSharedFlow<WordlistEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class WordlistEvent {
        data class OpenWordBottomSheet(val word: Word) : WordlistEvent()
        data class SpeakWord(val word: String) : WordlistEvent()
    }

    private val wordsFlow = combine(
        catNameFlow,
        searchQuery.asFlow()
    ) { catName, query ->
        Pair(catName, query)
    }.flatMapLatest { (catName, query) ->
        drillerUseCases.observeWordsSearchQueryUseCase.invoke(catName, query)
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
        drillerUseCases.updateCatNameStorageUseCase.invoke(catName)
    }

    fun openWordBottomSheet(word: Word) = viewModelScope.launch {
        _eventFlow.emit(WordlistEvent.OpenWordBottomSheet(word))
    }

    fun speakWord(word: String) = viewModelScope.launch {
        _eventFlow.emit(WordlistEvent.SpeakWord(word))
    }
}

class WordlistViewModelFactory @AssistedInject constructor(
    private val drillerUseCases: DrillerUseCases,
    @Assisted owner: SavedStateRegistryOwner,
) : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = WordlistViewModel(drillerUseCases, handle) as T
}

@AssistedFactory
interface WordlistViewModelAssistedFactory {
    fun create(owner: SavedStateRegistryOwner): WordlistViewModelFactory
}






