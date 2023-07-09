package space.rodionov.porosenokpetr.feature_wordeditor.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.model.WordUi
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
    val wordUi: WordUi = WordUi("Хуй", "Хуй", "Dick", "Kuk", "Swearings"),
)

sealed class WordEditorEvent(

)