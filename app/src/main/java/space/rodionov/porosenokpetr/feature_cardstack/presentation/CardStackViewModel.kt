package space.rodionov.porosenokpetr.feature_cardstack.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.domain.use_case.CollectModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectNativeLanguageUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectTranslationDirectionUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SpeakWordUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateLearnedPercentInCategoryUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateWordUseCase
import space.rodionov.porosenokpetr.core.util.Constants.MAX_STACK_SIZE
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.GetTenWordsUseCase
import space.rodionov.porosenokpetr.feature_cardstack.presentation.mapper.toWord
import space.rodionov.porosenokpetr.feature_cardstack.presentation.mapper.toWordUi
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem
import space.rodionov.porosenokpetr.main.navigation.sub_graphs.VocabularyDestinations

class CardStackViewModel(
    private val getTenWordsUseCase: GetTenWordsUseCase,
    private val collectModeUseCase: CollectModeUseCase,
    private val collectNativeLanguageUseCase: CollectNativeLanguageUseCase,
    private val collectTranslationDirectionUseCase: CollectTranslationDirectionUseCase,
    private val updateWordUseCase: UpdateWordUseCase,
    private val updateLearnedPercentInCategoryUseCase: UpdateLearnedPercentInCategoryUseCase,
    private val speakWordUseCase: SpeakWordUseCase
) : ViewModel() {

    var state by mutableStateOf(CardstackState())
        private set

    private val _uiEffect = Channel<UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        viewModelScope.launch {
            state = state.copy(words = getTenWords())
        }

        collectModeUseCase.invoke().onEach { mode ->
            state = state.copy(words = state.words.map { it.copy(mode = mode) })
        }.launchIn(viewModelScope)

        collectNativeLanguageUseCase.invoke().onEach { language ->
            state = state.copy(words = state.words.map { it.copy(nativeLang = language) })
        }.launchIn(viewModelScope)

        collectTranslationDirectionUseCase.invoke().onEach { nativeToForeign ->
            state = state.copy(words = state.words.map {
                it.copy(isNativeToForeign = nativeToForeign)
            })
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: CardstackEvent) {
        when (event) {
            is CardstackEvent.UpdateCurrentPosition -> {
                state = state.copy(currentPosition = event.position)
                if (needAddTenMoreWords(event.position)) {
                    viewModelScope.launch {
                        val newList = mutableListOf<CardStackItem.WordUi>().apply {
                            addAll(state.words)
                            addAll(getTenWords())
                        }
                        state = state.copy(words = newList)
                    }
                }
            }

            is CardstackEvent.UpdateWordStatus -> {
                viewModelScope.launch {
                    val word =
                        (state.words[state.currentPosition] as? CardStackItem.WordUi)?.toWord()
                    word?.let {
                        updateWordUseCase.invoke(it.copy(wordStatus = event.status))
                        updateLearnedPercentInCategoryUseCase.invoke(it.categoryName)
                    }
                }
            }

            is CardstackEvent.OnSpeakWordClick -> {
                speakWordUseCase.invoke(event.word)
            }

            is CardstackEvent.OnEditWordClick -> {
                viewModelScope.launch {
                    _uiEffect.send(
                        UiEffect.NavigateTo(
                            "${VocabularyDestinations.WordEditor.route}/${event.word.id}"
                        )
                    )
                }
            }
        }
    }

    private suspend fun getTenWords(): List<CardStackItem.WordUi> {
        val mode = collectModeUseCase.invoke().first()
        val nativeLanguage = collectNativeLanguageUseCase.invoke().first()
        val isNativeToForeign = collectTranslationDirectionUseCase.invoke().first()
        val tenWords = getTenWordsUseCase.invoke().map {
            it.toWordUi().copy(
                mode = mode,
                nativeLang = nativeLanguage,
                isNativeToForeign = isNativeToForeign
            )
        }
        return tenWords
    }

    private fun needAddTenMoreWords(currentPosition: Int): Boolean {
        return currentPosition == state.words.size - 3 && currentPosition < MAX_STACK_SIZE - 10
    }
}

data class CardstackState(
    val words: List<CardStackItem.WordUi> = emptyList(),
    val currentPosition: Int = 0
)

sealed class CardstackEvent {
    data class UpdateCurrentPosition(val position: Int) : CardstackEvent()
    data class UpdateWordStatus(val status: Int) : CardstackEvent()
    data class OnSpeakWordClick(val word: String) : CardstackEvent()
    data class OnEditWordClick(val word: CardStackItem.WordUi) : CardstackEvent()
}