package space.rodionov.porosenokpetr.feature_wordeditor.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_EN
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_RU
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_SE
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_UA
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.model.Translation
import javax.inject.Inject

class WordEditorViewModel @Inject constructor(

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
        Translation(LANGUAGE_RU, "Хуй"),
        Translation(LANGUAGE_UA, "Хуй"),
        Translation(LANGUAGE_EN, "Dick"),
        Translation(LANGUAGE_SE, "Kuk")
    )
)

sealed class WordEditorEvent {
    object OnBackClick : WordEditorEvent()
    data class OnTranslationChanged(
        val language: Int,
        val translation: String
    ) : WordEditorEvent()
}