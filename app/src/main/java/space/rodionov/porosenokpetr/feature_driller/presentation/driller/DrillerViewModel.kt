package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.GetTenWordsUseCase
import javax.inject.Inject

private const val TAG = "DrillerViewModel"
@HiltViewModel
class DrillerViewModel @Inject constructor(
    private val getTenWordsUseCase: GetTenWordsUseCase
): ViewModel() {

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words = _words.asStateFlow()

    fun launchShit() = viewModelScope.launch {
            Log.d(TAG, "ViewModel: CALLED")
            _words.value = getTenWordsUseCase()
        }

}