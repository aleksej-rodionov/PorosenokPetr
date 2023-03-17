package space.rodionov.porosenokpetr.feature_vocabulary.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import space.rodionov.porosenokpetr.core.domain.model.Word
import javax.inject.Inject

class VocabularyViewModel @Inject constructor(

): ViewModel() {

    var state by mutableStateOf(VocabularyState())
        private set

    fun onEvent(event: VocabularyEvent) {
        when (event) {
            is VocabularyEvent.OnSearchFocusChanged -> {
                state = state.copy(
                    showSearchHint = !event.isFocused && state.searchQuery.isBlank()
                )
            }
            is VocabularyEvent.OnSearchQueryChanged -> {
                state = state.copy(
                    searchQuery = event.query
                )
            }
        }
    }
}



data class VocabularyState(
    val list: List<Word> = emptyList(),
    val searchQuery: String = "",
    val showSearchHint: Boolean = false
)

sealed class VocabularyEvent {

    data class OnSearchQueryChanged(val query: String): VocabularyEvent()

    data class OnSearchFocusChanged(val isFocused: Boolean): VocabularyEvent()
}