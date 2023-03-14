package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.domain.use_case.PreferencesUseCases
import space.rodionov.porosenokpetr.feature_driller.di.ViewModelAssistedFactory
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.DrillerUseCases
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_EN
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_RU
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_PETR
import javax.inject.Inject

class WordlistViewModel(
    private val drillerUseCases: DrillerUseCases,
    private val preferencesUseCases: PreferencesUseCases,
    private val state: SavedStateHandle,
) : ViewModel() {

    private var catName: String = ""

    private val _nativeLang = drillerUseCases.observeNativeLangUseCase.invoke()
    val nativeLang = _nativeLang.stateIn(viewModelScope, SharingStarted.Lazily, LANGUAGE_RU)

    private val _learnedLang = drillerUseCases.observeLearnedLangUseCase.invoke()
    val learnedLang = _learnedLang.stateIn(viewModelScope, SharingStarted.Lazily, LANGUAGE_EN)

    var catToSearchIn = state.getLiveData<Category>("category", null)

    val searchQuery = state.getLiveData("searchQuery", "")

    //==================================MODE==========================
    private val _mode = preferencesUseCases.observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, MODE_LIGHT)

    //==============================UI EVENTS==============================
    private val _eventFlow = MutableSharedFlow<WordlistEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class WordlistEvent {
        data class OpenWordBottomSheet(val word: Word) : WordlistEvent()
        data class SpeakWord(val word: String) : WordlistEvent()
    }

    //todo fix
    private val wordsFlow = drillerUseCases.observeWordsSearchQueryUseCase.invoke(
        catName,
        searchQuery.value ?: ""
    )

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

    fun updateCatStorage(catName: String) {
        this.catName = catName
    }

    fun openWordBottomSheet(word: Word) = viewModelScope.launch {
        _eventFlow.emit(WordlistEvent.OpenWordBottomSheet(word))
    }

    fun speakWord(word: String) = viewModelScope.launch {
        _eventFlow.emit(WordlistEvent.SpeakWord(word))
    }
}

class WordlistViewModelFactory @Inject constructor(
    private val drillerUseCases: DrillerUseCases,
    private val preferencesUseCases: PreferencesUseCases
) : ViewModelAssistedFactory<WordlistViewModel> {
    override fun create(handle: SavedStateHandle): WordlistViewModel {
        return WordlistViewModel(drillerUseCases, preferencesUseCases, handle)
    }
}

//class WordlistViewModelFactory @AssistedInject constructor(
//    private val drillerUseCases: DrillerUseCases,
//    @Assisted owner: SavedStateRegistryOwner,
//) : AbstractSavedStateViewModelFactory(owner, null) {
//    override fun <T : ViewModel?> create(
//        key: String,
//        modelClass: Class<T>,
//        handle: SavedStateHandle
//    ): T = WordlistViewModel(drillerUseCases, handle) as T
//}
//
//@AssistedFactory
//interface WordlistViewModelAssistedFactory {
//    fun create(owner: SavedStateRegistryOwner): WordlistViewModelFactory
//}






