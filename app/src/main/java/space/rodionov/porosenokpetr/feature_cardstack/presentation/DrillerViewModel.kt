package space.rodionov.porosenokpetr.feature_cardstack.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.util.Resource
import space.rodionov.porosenokpetr.core.util.ViewModelAssistedFactory
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.use_case.SharedUseCases
import space.rodionov.porosenokpetr.core.util.Constants
import space.rodionov.porosenokpetr.core.util.Constants.TAG_PETR
import javax.inject.Inject

class DrillerViewModel (
    private val sharedUseCases: SharedUseCases,
    private val preferenvesUseCases: PreferencesUseCases,
    private val state: SavedStateHandle,
) : ViewModel() {
    var savedPosition = state.get<Int>("savedPos") ?: 0
        set(value) {
            field = value
            state.set("savedPos", value)
        }

    var rememberPositionAfterSwitchingFragment = state.get<Boolean>("memorizePosInBackstack") ?: false
        set(value) {
            field = value
            state.set("memorizePosInBackstack", value)
        }

    var rememberPositionAfterDestroy = state.get<Boolean>("memorizePosOnDestroy") ?: false
        set(value) {
            field = value
            state.set("memorizePosOnDestroy", value)
        }

    private val _transDir = sharedUseCases.observeTranslationDirectionUseCase.invoke()
    val transDir = _transDir.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _mode = preferenvesUseCases.observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    private val _nativeLang = sharedUseCases.observeNativeLangUseCase.invoke()
    val nativeLang= _nativeLang.stateIn(viewModelScope, SharingStarted.Lazily, Constants.LANGUAGE_RU)

    private val _learnedLang = sharedUseCases.observeLearnedLangUseCase.invoke()
    val learnedLang= _learnedLang.stateIn(viewModelScope, SharingStarted.Lazily, Constants.LANGUAGE_EN)

    private val snapshotCatsInCaseUncheckAll = mutableListOf<String>()
    var rememberPositionAfterChangingStack = false

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition = _currentPosition.asStateFlow() // todo сохранять currentPosition в savedStateHandle

    private val _categories = sharedUseCases.observeAllCategoriesUseCase.invoke()
    val categories = _categories.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _wordsState = MutableStateFlow(WordState())
    val wordsState = _wordsState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<DrillerEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class DrillerEvent {
        data class ShowSnackbar(val msg: String) : DrillerEvent()
        object ScrollToCurrentPosition : DrillerEvent()
        object ScrollToSavedPosition : DrillerEvent()
        object NavigateToCollectionScreen : DrillerEvent()
        object NavigateToSettings : DrillerEvent()
        object OpenFilterBottomSheet : DrillerEvent()
        data class SpeakWord(val word: String) : DrillerEvent()
    }

    init {
        newRound()
        makeSnapshot()
    }

    fun addTenWords() = viewModelScope.launch {
//        Log.d(TAG_PETR, "VM addTenWords: CALLED")
        sharedUseCases.getTenWordsUseCase().onEach { result -> // onEach = on each emission of the flow
            val oldPlusNewWords = mutableListOf<Word>()
            oldPlusNewWords.addAll(wordsState.value.words)

            when (result) {
                is Resource.Loading -> {
                    oldPlusNewWords.addAll(result.data ?: emptyList())
                    _wordsState.value = wordsState.value.copy(
                        words = oldPlusNewWords,
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    oldPlusNewWords.addAll(result.data ?: emptyList())
                    _wordsState.value = wordsState.value.copy(
                        words = oldPlusNewWords,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    // этот вариант (ошибка) никогда не приходит, т.к. в repo никогда не эмиттится
                    _wordsState.value = wordsState.value.copy(
                        words = wordsState.value.words ?: mutableListOf(),
                        isLoading = false
                        // todo обработать ошибку?
                    )
                }
            }
        }.launchIn(this) // this относится к viewModelScope, в котором onEach этот завернут
    }

    fun newRound() {
        _wordsState.value = WordState()
        addTenWords()
        updateCurrentPosition(0)
    }

    fun updateCurrentPosition(pos: Int) {
        Log.d(TAG_PETR, "updateCurrentPosition: $pos")
        _currentPosition.value = pos // костыль
//        updateSavedPosition(pos)
    }

    fun onCardAppeared(position: Int) {
        if (rememberPositionAfterChangingStack) {
            scrollToCurPos()
            updateCurrentPosition(currentPosition.value)
            rememberPositionAfterChangingStack = false
        } else {
            updateCurrentPosition(position)
        }
    }

    fun updateSavedPosition(pos: Int) {
        savedPosition = pos
    }

    fun inactivateCurrentWord() = viewModelScope.launch {
        val word = wordsState.value.words[currentPosition.value]
        sharedUseCases.updateWordIsActiveUseCase(word, false)
    }

//====================METHODS FOR BOTTOMSHEET CHIPGROUP================================

    fun onChipTurnedOn(catName: String) = viewModelScope.launch {
        if (checkIfOnlyOneInactiveCat()) {
            makeSnapshot()
        }
        activateCategory(catName)
    }

    fun onChipTurnedOff(catName: String) = viewModelScope.launch {
        inactivateCategory(catName)
    }

    fun onCheckBoxTurnedOn() = viewModelScope.launch {
        makeSnapshot()
        val allCats = sharedUseCases.getAllCatsNamesUseCase.invoke()
        allCats.forEach { catName ->
            activateCategory(catName)
        }
//        Log.d(TAG_PETR, "onCheckBoxTurnedOn: allActive.size = ${getAllActiveCatsNamesUseCase.invoke().size}")
//        Log.d(TAG_PETR, "onCheckBoxTurnedOn: snapshot.size = ${snapshotCatsInCaseUncheckAll.size}")
    }

    fun onCheckBoxTurnedOff() = viewModelScope.launch {
        val allCatsNames = sharedUseCases.getAllCatsNamesUseCase.invoke()
        allCatsNames.forEach { name ->
            if (!snapshotCatsInCaseUncheckAll.contains(name)) {
                inactivateCategory(name)
            }
        }
    }

    private suspend fun checkIfOnlyOneInactiveCat(): Boolean {
        val allCats = sharedUseCases.getAllCatsNamesUseCase.invoke()
        val allActiveCats = sharedUseCases.getAllActiveCatsNamesUseCase.invoke()
        return allCats.size - allActiveCats.size == 1
    }

    private fun addCatNameToSnapshot(name: String) {
        if (!snapshotCatsInCaseUncheckAll.contains(name)) {
            snapshotCatsInCaseUncheckAll.add(name)
        }
    }

    private fun refulfillSnapshotByNewNames(newNames: List<String>) {
        snapshotCatsInCaseUncheckAll.clear()
        newNames.forEach { name ->
            addCatNameToSnapshot(name)
        }
    }

    private fun activateCategory(catName: String) = viewModelScope.launch {
        sharedUseCases.makeCategoryActiveUseCase(catName, true)
    }

    private suspend fun inactivateCategory(catName: String) {
        sharedUseCases.makeCategoryActiveUseCase(catName, false)
    }

    fun showNotLessThanOneCategory(msg: String) = viewModelScope.launch {
        _eventFlow.emit(DrillerEvent.ShowSnackbar(msg))
    }

    private fun makeSnapshot() = viewModelScope.launch {
        val allCats = sharedUseCases.getAllCatsNamesUseCase.invoke()
        val allActiveCats = sharedUseCases.getAllActiveCatsNamesUseCase.invoke()
        if (allActiveCats.size == allCats.size && allActiveCats.isNotEmpty()) {
            val singleCat = listOf(allActiveCats[0])
            refulfillSnapshotByNewNames(singleCat)
        } else {
            refulfillSnapshotByNewNames(allActiveCats)
        }
//        Log.d(TAG_PETR, "snapshot created. Size = ${snapshotCatsInCaseUncheckAll.size}")
    }

    fun scrollToCurPos() = viewModelScope.launch {
        _eventFlow.emit(DrillerEvent.ScrollToCurrentPosition)
//        Log.d(TAG_PETR, "scrollToCurPos: CALLED, curPos = ${currentPosition.value}")
    }

    fun scrollToSavedPosIfItIsSaved() = viewModelScope.launch {
        if (rememberPositionAfterSwitchingFragment || rememberPositionAfterDestroy) {
            _eventFlow.emit(DrillerEvent.ScrollToSavedPosition)
//            Log.d(TAG_PETR, "scrollToSavedPos: CALLED, savedPos = $savedPosition")
        } else {
//            Log.d(TAG_PETR, "saved Position is not remembered")
        }
    }

    fun acceptCatListChange() = viewModelScope.launch {
        val wholeList = mutableListOf<Word>()
        wholeList.addAll(wordsState.value.words)
        _wordsState.value = wordsState.value.copy(
            words = wholeList,
            isLoading = true
        )
//        Log.d(TAG_PETR, "wholeList.size = ${wholeList.size}, curPos = ${currentPosition.value}, curPosWord = ${wholeList.elementAt(currentPosition.value)}")
        delay(500L)
        val newWholeList = mutableListOf<Word>()
        val allActiveCatsNames = sharedUseCases.getAllActiveCatsNamesUseCase.invoke()
        newWholeList.addAll(wholeList.map { word ->
            if (!sharedUseCases.isCategoryActiveUseCase.invoke(word.categoryName)) {
                val newWord = sharedUseCases.getRandomWordUseCase.invoke(allActiveCatsNames)
                newWord
            } else word
        })
        _wordsState.value = wordsState.value.copy(
            words = newWholeList,
            isLoading = false
        )
        rememberPositionAfterChangingStack = true
//        Log.d(TAG_PETR, "newWholeList.size = ${newWholeList.size}, curPos = ${currentPosition.value}, curPosWord = ${wholeList.elementAt(currentPosition.value)}")
    }

    fun navigateToCollectionScreen() = viewModelScope.launch {
        _eventFlow.emit(DrillerEvent.NavigateToCollectionScreen)
    }

    fun navigateToSettings() = viewModelScope.launch {
        _eventFlow.emit(DrillerEvent.NavigateToSettings)
    }

    fun openFilterBottomSheet() = viewModelScope.launch {
        _eventFlow.emit(DrillerEvent.OpenFilterBottomSheet)
    }

    fun rememberPositionAfterSwitchFragment() {
        updateSavedPosition(currentPosition.value)
        rememberPositionAfterSwitchingFragment = true
    }

    fun rememberPositionInCaseOfDestroy() {
        updateSavedPosition(currentPosition.value)
        rememberPositionAfterDestroy = true
    }

    fun speakWord(word: String) = viewModelScope.launch {
        _eventFlow.emit(DrillerEvent.SpeakWord(word))
    }
}

data class WordState(
    val words: MutableList<Word> = mutableListOf(),
    val isLoading: Boolean = false
)

class DrillerViewModelFactory @Inject constructor(
    private val sharedUseCases: SharedUseCases,
    private val preferenvesUseCases: PreferencesUseCases
) : ViewModelAssistedFactory<DrillerViewModel> {
    override fun create(handle: SavedStateHandle): DrillerViewModel {
        return DrillerViewModel(sharedUseCases, preferenvesUseCases, handle)
    }
}

//class DrillerViewModelFactory @AssistedInject constructor(
//    private val drillerUseCases: DrillerUseCases,
//    @Assisted owner: SavedStateRegistryOwner,
//) : AbstractSavedStateViewModelFactory(owner, null) {
//    override fun <T : ViewModel?> create(
//        key: String,
//        modelClass: Class<T>,
//        handle: SavedStateHandle
//    ): T = DrillerViewModel(drillerUseCases, handle) as T
//}
//
//@AssistedFactory
//interface DrillerViewModelAssistedFactory {
//    fun create(owner: SavedStateRegistryOwner): DrillerViewModelFactory
//}
