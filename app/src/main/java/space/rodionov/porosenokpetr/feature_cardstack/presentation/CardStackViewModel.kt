package space.rodionov.porosenokpetr.feature_cardstack.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SharedUseCases
import space.rodionov.porosenokpetr.core.util.Constants.MAX_STACK_SIZE
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.CardStackUseCases
import space.rodionov.porosenokpetr.feature_cardstack.presentation.mapper.toWord
import space.rodionov.porosenokpetr.feature_cardstack.presentation.mapper.toWordUi
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem
import javax.inject.Inject

class CardStackViewModel @Inject constructor(
    private val sharedUseCases: SharedUseCases,
    private val cardStackUseCases: CardStackUseCases,
) : ViewModel() {

    var state by mutableStateOf(CardstackState())
        private set

    init {
        viewModelScope.launch {
            val tenWordsMore = cardStackUseCases.getTenWordsUseCase.invoke().map {
                it.toWordUi()
            }
            state = state.copy(words = tenWordsMore)
        }

        sharedUseCases.observeModeUseCase.invoke().onEach { mode ->
//            state = state.copy(mode = it)
            state = state.copy(words = state.words.map { it.copy(mode = mode) })
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: CardstackEvent) {
        when (event) {
            is CardstackEvent.UpdateCurrentPosition -> {
                state = state.copy(currentPosition = event.position)
                if (event.position == state.words.size - 3 &&
                    event.position < MAX_STACK_SIZE - 10
                ) {
                    viewModelScope.launch {
                        val tenWordsMore = cardStackUseCases.getTenWordsUseCase.invoke().map {
                            it.toWordUi()
                        }
                        val newList = mutableListOf<CardStackItem.WordUi>().apply {
                            addAll(state.words)
                            addAll(tenWordsMore)
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
                        sharedUseCases.updateWordStatusUseCase.invoke(
                            it,
                            event.status
                        )
                        sharedUseCases.updateLearnedPercentInCategory.invoke(it.categoryName)
                    }
                }
            }

            is CardstackEvent.SpeakWord -> {
                sharedUseCases.speakWord.invoke(event.word)
            }
        }
    }
}

data class CardstackState(
    val words: List<CardStackItem.WordUi> = emptyList(),
    val currentPosition: Int = 0,
    val mode: Int = MODE_LIGHT
)

sealed class CardstackEvent {
    data class UpdateCurrentPosition(val position: Int) : CardstackEvent()
    data class UpdateWordStatus(val status: Int) : CardstackEvent()
    data class SpeakWord(val word: String) : CardstackEvent()
}