package space.rodionov.porosenokpetr.feature_wordeditor.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
import javax.inject.Inject

class WordEditorViewModel @Inject constructor(

): ViewModel() {

    var state by mutableStateOf(WordEditorState())
        private set

    private val _uiEffect = Channel<UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun onEvent(event: WordEditorEvent) {
        when (event) {
            //todo
        }
    }
}

data class WordEditorState(
    val wordUi: VocabularyItem.WordUi = VocabularyItem.WordUi(
        "Хуй",
        "Хуй",
        "Dick",
        "Kuk",
        categoryName = "Swearings"
    )
)

sealed class WordEditorEvent(

)