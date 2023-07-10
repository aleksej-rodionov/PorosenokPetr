package space.rodionov.porosenokpetr.feature_wordeditor.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.domain.use_case.GetWordByIdUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateWordUseCase
import space.rodionov.porosenokpetr.core.presentation.UiText
import space.rodionov.porosenokpetr.core.util.Constants.DEFAULT_INT
import space.rodionov.porosenokpetr.core.util.Constants.DEFAULT_STRING
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.mapper.toWord
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.mapper.toWordUi
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.model.Translation
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.model.WordUi
import javax.inject.Inject

class WordEditorViewModel(
    private val getWordByIdUseCase: GetWordByIdUseCase,
    private val updateWordUseCase: UpdateWordUseCase
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

            is WordEditorEvent.OnReceivedWordId -> {
                viewModelScope.launch {
                    state = state.copy(wordId = event.id)
                    val word =
                        getWordByIdUseCase.invoke(event.id.toIntOrNull() ?: DEFAULT_INT)?.toWordUi()
                    word?.let { setWord(it) }
                        ?: _uiEffect.send(UiEffect.ShowSnackbar(UiText.DynamicString("Word not found")))
                }
            }

            is WordEditorEvent.OnSaveClick -> {
                viewModelScope.launch {
                    state.word?.let {
                        updateWordUseCase.invoke(it.copy(
                            rus = state.translations.find {
                                it.language == Language.Russian
                            }?.translation ?: DEFAULT_STRING,
                            ukr = state.translations.find {
                                it.language == Language.Ukrainian
                            }?.translation ?: DEFAULT_STRING,
                            eng = state.translations.find {
                                it.language == Language.English
                            }?.translation ?: DEFAULT_STRING,
                            swe = state.translations.find {
                                it.language == Language.Swedish
                            }?.translation ?: DEFAULT_STRING,
                        ).toWord())

                        _uiEffect.send(UiEffect.NavigateUp)
                    }
                }
            }
        }
    }

    private fun setWord(word: WordUi) {
        val translations = listOf(
            Translation(Language.Russian, word.rus),
            Translation(Language.Ukrainian, word.ukr ?: DEFAULT_STRING),
            Translation(Language.English, word.eng),
            Translation(Language.Swedish, word.swe ?: DEFAULT_STRING),
        )
        state = state.copy(
            word = word,
            translations = translations
        )
    }
}

data class WordEditorState(
    val wordId: String? = null,
    val word: WordUi? = null,
    val translations: List<Translation> = listOf()
)

sealed class WordEditorEvent {
    object OnBackClick : WordEditorEvent()
    data class OnTranslationChanged(
        val language: Language,
        val translation: String
    ) : WordEditorEvent()

    data class OnReceivedWordId(val id: String) : WordEditorEvent()
    object OnSaveClick : WordEditorEvent()
}