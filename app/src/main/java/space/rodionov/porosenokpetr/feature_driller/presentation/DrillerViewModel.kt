package space.rodionov.porosenokpetr.feature_driller.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.GetTenWordsUseCase
import javax.inject.Inject

@HiltViewModel
class DrillerViewModel @Inject constructor(
    private val getTenWordsUseCase: GetTenWordsUseCase
): ViewModel() {

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words = _words.asStateFlow()


}