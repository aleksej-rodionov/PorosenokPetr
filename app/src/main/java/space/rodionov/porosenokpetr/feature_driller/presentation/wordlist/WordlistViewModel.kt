package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.util.ViewModelAssistedFactory
import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.use_case.SharedUseCases
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_EN
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_RU
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.core.util.Constants.TAG_PETR
import javax.inject.Inject

class WordlistViewModel(
    private val sharedUseCases: SharedUseCases,
    private val preferencesUseCases: PreferencesUseCases,
    private val state: SavedStateHandle,
) : ViewModel() {

    private var catName: String = ""

    private val _nativeLang = sharedUseCases.observeNativeLangUseCase.invoke()
    val nativeLang = _nativeLang.stateIn(viewModelScope, SharingStarted.Lazily, LANGUAGE_RU)

    private val _learnedLang = sharedUseCases.observeLearnedLangUseCase.invoke()
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
    private val wordsFlow = sharedUseCases.observeWordsSearchQueryUseCase.invoke(
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
    private val sharedUseCases: SharedUseCases,
    private val preferencesUseCases: PreferencesUseCases
) : ViewModelAssistedFactory<WordlistViewModel> {
    override fun create(handle: SavedStateHandle): WordlistViewModel {
        return WordlistViewModel(sharedUseCases, preferencesUseCases, handle)
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






