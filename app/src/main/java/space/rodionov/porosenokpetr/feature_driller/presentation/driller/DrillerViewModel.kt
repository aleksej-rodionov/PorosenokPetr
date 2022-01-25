package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.Resource
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.GetTenWordsUseCase
import javax.inject.Inject

private const val TAG = "DrillerViewModel"

@HiltViewModel
class DrillerViewModel @Inject constructor(
    private val getTenWordsUseCase: GetTenWordsUseCase
) : ViewModel() {

    private val _wordsState = MutableStateFlow(WordState())
    val wordsState = _wordsState.asStateFlow()

    fun loadTenWords() = viewModelScope.launch {
        Log.d(TAG, "ViewModel: CALLED")
        getTenWordsUseCase().onEach { result -> // onEach = on each emission of the flow
            when (result) {
                is Resource.Loading -> {
                    _wordsState.value = wordsState.value.copy(
                        words = result.data ?: emptyList(),
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _wordsState.value = wordsState.value.copy(
                        words = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _wordsState.value = wordsState.value.copy(
                        words = result.data ?: emptyList(),
                        isLoading = false
                    // todo обработать ошибку?
                    )
                }
            }
        }.launchIn(this) // this относится к viewModelScope, в котором onEach этот завернут
    }

}

data class WordState(
    val words: List<Word> = emptyList(),
    val isLoading: Boolean = false
)