package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.Resource
import space.rodionov.porosenokpetr.feature_driller.Constants.TAG_PETR
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.GetTenWordsUseCase
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.ObserveAllCategoriesUseCase
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.UpdateWordIsActiveUseCase
import javax.inject.Inject

@HiltViewModel
class DrillerViewModel @Inject constructor(
    private val getTenWordsUseCase: GetTenWordsUseCase,
    private val updateWordIsActiveUseCase: UpdateWordIsActiveUseCase,
    private val observeAllCategories: ObserveAllCategoriesUseCase
) : ViewModel() {

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition = _currentPosition.asStateFlow()

    private val _categories = observeAllCategories.invoke()
    val categories = _categories.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _wordsState = MutableStateFlow(WordState())
    val wordsState = _wordsState.asStateFlow()

    fun addTenWords() = viewModelScope.launch {
        Log.d(TAG_PETR, "VM addTenWords: CALLED")
        getTenWordsUseCase().onEach { result -> // onEach = on each emission of the flow
            val newWords = result.data ?: emptyList()
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
        Log.d(TAG_PETR, "updateCurrentPosition: pos = $pos")
        _currentPosition.value = pos
    }

    fun inactivateCurrentWord() = viewModelScope.launch {
        Log.d(TAG_PETR, "VM inactivateCurrentWord: CALLED")
        val word = wordsState.value.words[currentPosition.value]
        updateWordIsActiveUseCase(word, false)
    }
}

data class WordState(
    val words: List<Word> = emptyList(),
    val isLoading: Boolean = false
)