package space.rodionov.porosenokpetr.feature_wordeditor.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.model.Translation
import javax.inject.Inject

class WordEditorViewModel(

) : ViewModel() {

    var state by mutableStateOf(WordEditorState())
        private set

    private val _uiEffect = Channel<UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun onEvent(event: WordEditorEvent) {
        when (event) {
            is WordEditorEvent.OnBackClick -> {
                viewModelScope.launch { _uiEffect.send(UiEffect.NavigateUp) }
            }

            is WordEditorEvent.OnTranslationChanged -> {
                state = state.copy(translations = state.translations.map {
                    if (it.language == event.language) it.copy(translation = event.translation)
                    else it
                })
            }
        }
    }
}

data class WordEditorState(
    val translations: List<Translation> = listOf(
        Translation(Language.Russian, "Хуй"),
        Translation(Language.Ukrainian, "Хуй"),
        Translation(Language.English, "Dick"),
        Translation(Language.Swedish, "Kuk")
    )
)

sealed class WordEditorEvent {
    object OnBackClick : WordEditorEvent()
    data class OnTranslationChanged(
        val language: Language,
        val translation: String
    ) : WordEditorEvent()
}