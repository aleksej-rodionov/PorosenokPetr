package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.Resource
import space.rodionov.porosenokpetr.feature_driller.Constants.TAG_PETR
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import javax.inject.Inject

@HiltViewModel
class DrillerViewModel @Inject constructor(
    private val getTenWordsUseCase: GetTenWordsUseCase,
    private val updateWordIsActiveUseCase: UpdateWordIsActiveUseCase,
    private val observeAllCategories: ObserveAllCategoriesUseCase,
    private val makeCategoryActiveUseCase: MakeCategoryActiveUseCase,
    private val getAllActiveCatsNamesUseCase: GetAllActiveCatsNamesUseCase,
    private val getAllCatsNamesUseCase: GetAllCatsNamesUseCase
) : ViewModel() {

    private val snapshotCatsInCaseUncheckAll = mutableListOf<String>()
    private var freezeSnapshot = false

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition = _currentPosition.asStateFlow()

    private val _categories = observeAllCategories.invoke()
    val categories = _categories.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _wordsState = MutableStateFlow(WordState())
    val wordsState = _wordsState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<DrillerEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class DrillerEvent {
        data class ShowSnackbar(val msg: String) : DrillerEvent()
    }

    init {
        makeSnapshot()
    }

    fun addTenWords() = viewModelScope.launch {
//        Log.d(TAG_PETR, "VM addTenWords: CALLED")
        getTenWordsUseCase().onEach { result -> // onEach = on each emission of the flow
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
                        words = result.data ?: emptyList(),
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
    }

    fun updateCurrentPosition(pos: Int) {
        _currentPosition.value = pos
    }

    fun inactivateCurrentWord() = viewModelScope.launch {
        val word = wordsState.value.words[currentPosition.value]
        updateWordIsActiveUseCase(word, false)
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
        val allCats = getAllCatsNamesUseCase.invoke()
        allCats.forEach { catName ->
            activateCategory(catName)
        }
        Log.d(TAG_PETR, "onCheckBoxTurnedOn: allActive.size = ${getAllActiveCatsNamesUseCase.invoke().size}")
        Log.d(TAG_PETR, "onCheckBoxTurnedOn: snapshot.size = ${snapshotCatsInCaseUncheckAll.size}")
    }

    fun onCheckBoxTurnedOff() = viewModelScope.launch {
        val allCatsNames = getAllCatsNamesUseCase.invoke()
        allCatsNames.forEach { name ->
            if (!snapshotCatsInCaseUncheckAll.contains(name)) {
                inactivateCategory(name)
            }
        }
    }

    private suspend fun checkIfOnlyOneInactiveCat(): Boolean {
        val allCats = getAllCatsNamesUseCase.invoke()
        val allActiveCats = getAllActiveCatsNamesUseCase.invoke()
        return allCats.size - allActiveCats.size == 1
    }

    private fun addCatNameToSnapshot(name: String) {
        if (!snapshotCatsInCaseUncheckAll.contains(name)) {
            snapshotCatsInCaseUncheckAll.add(name)
        }
    }

    private fun refulfillSnapshotByNewNames(newNames: List<String>) {
//        if (!freezeSnapshot) {
            snapshotCatsInCaseUncheckAll.clear()
            newNames.forEach { name ->
                addCatNameToSnapshot(name)
            }
//        } else {
//            Log.d(TAG_PETR, "Cannot refulfillSnapshotByNewNames: snapshot is frozen")
//        }
    }

    private fun activateCategory(catName: String) = viewModelScope.launch {
        makeCategoryActiveUseCase(catName, true)
    }

    private suspend fun inactivateCategory(catName: String) {
        makeCategoryActiveUseCase(catName, false)
    }

    fun showNotLessThanOneCategory(msg: String) = viewModelScope.launch {
        _eventFlow.emit(DrillerEvent.ShowSnackbar(msg))
    }

    private fun makeSnapshot() = viewModelScope.launch {
        val allCats = getAllCatsNamesUseCase.invoke()
        val allActiveCats = getAllActiveCatsNamesUseCase.invoke()
        if (allActiveCats.size == allCats.size && allActiveCats.isNotEmpty()) {
            val singleCat = listOf(allActiveCats[0])
            refulfillSnapshotByNewNames(singleCat)
        } else {
            refulfillSnapshotByNewNames(allActiveCats)
        }
        Log.d(TAG_PETR, "snapshot created. Size = ${snapshotCatsInCaseUncheckAll.size}")
    }
}

data class WordState(
    val words: List<Word> = emptyList(),
    val isLoading: Boolean = false
)