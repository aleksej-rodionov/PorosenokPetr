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
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_PETR
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_EN
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_RU
import javax.inject.Inject

@HiltViewModel
class WordlistViewModel @Inject constructor(
    private val catNameFromStorageUseCase: CatNameFromStorageUseCase,
    private val updateCatNameStorageUseCase: UpdateCatNameStorageUseCase,
    private val observeWordsSearchQueryUseCase: ObserveWordsSearchQueryUseCase,
    private val observeWordUseCase: ObserveWordUseCase,
    private val updateWordIsActiveUseCase: UpdateWordIsActiveUseCase,
    private val updateIsWordActiveUseCase: UpdateIsWordActiveUseCase,
    private val observeModeUseCase: ObserveModeUseCase,
    private val observeNativeLangUseCase: ObserveNativeLangUseCase,
    private val observeLearnedLangUseCase: ObserveLearnedLangUseCase,
    private val state: SavedStateHandle
) : ViewModel() {
//    var nativeLangLiveData = state.getLiveData<Int?>("nativeLangLiveData", null)
    var nativLivedata = state.getLiveData<String>("nativLivedata", null)
    var foreignLivedata = state.getLiveData<String>("foreignLivedata", null)
    var catNameLivedata = state.getLiveData<String?>("catNameLivedata", null)

    private val _word = combine(
        nativLivedata.asFlow(),
        foreignLivedata.asFlow(),
        catNameLivedata.asFlow()
    ) { rus, eng, catName ->
        Triple(rus, eng, catName)
    }.flatMapLatest { (rus, eng, catName) ->
        observeWordUseCase.invoke(rus, eng, catName)
    }
    val word = _word.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _nativeLang = observeNativeLangUseCase.invoke()
    val nativeLang = _nativeLang.stateIn(viewModelScope, SharingStarted.Lazily, LANGUAGE_RU)

    private val _learnedLang = observeLearnedLangUseCase.invoke()
    val learnedLang= _learnedLang.stateIn(viewModelScope, SharingStarted.Lazily, LANGUAGE_EN)

    var catToSearchIn = state.getLiveData<Category>("category", null)
    val catNameFlow = catNameFromStorageUseCase.invoke()

    val searchQuery = state.getLiveData("searchQuery", "")

    private val _mode = observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, MODE_LIGHT)

    private val _eventFlow = MutableSharedFlow<WordlistEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class WordlistEvent {
        data class OpenWordBottomSheet(val word: Word) : WordlistEvent()
        data class SpeakWord(val word: String) :WordlistEvent()
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

    fun openWordBottomSheet(word: Word) = viewModelScope.launch {
//        wordInDialog.value = word
        _eventFlow.emit(WordlistEvent.OpenWordBottomSheet(word))
    }

    fun activateWord() = viewModelScope.launch {
        nativLivedata.value?.let { nativ ->
            foreignLivedata.value?.let { foreign ->
                catNameLivedata.value?.let { catName ->
                    updateIsWordActiveUseCase.invoke(nativ, foreign, catName, true)
                }
            }
        }
    }

    fun inactivateWord() = viewModelScope.launch {
        nativLivedata.value?.let { nativ ->
            foreignLivedata.value?.let { foreign ->
                catNameLivedata.value?.let { catName ->
                    updateIsWordActiveUseCase.invoke(nativ, foreign, catName, false)
                }
            }
        }
    }

    fun speakWord(word: String) = viewModelScope.launch {
        _eventFlow.emit(WordlistEvent.SpeakWord(word))
    }
}







