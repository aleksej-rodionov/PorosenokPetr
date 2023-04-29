package space.rodionov.porosenokpetr.feature_settings.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.domain.use_case.SharedUseCases
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_RU
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.SettingsUseCases
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularyEvent
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val settingsUseCases: SettingsUseCases,
    private val sharedUseCases: SharedUseCases
) : ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    private val _uiEffect = Channel<UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnBackClick -> {

            }

            is SettingsEvent.OnModeChanged -> {
                viewModelScope.launch { sharedUseCases.setModeUseCase.invoke(event.mode) }
            }

            is SettingsEvent.OnFollowSystemModeChanged -> {
                viewModelScope.launch { settingsUseCases.setFollowSystemModeUseCase.invoke(event.follow) }
            }

            is SettingsEvent.OnNativeLanguageChanged -> {

            }
        }
    }

    init {
        sharedUseCases.observeModeUseCase.invoke().onEach {
            state = state.copy(mode = it)
        }.launchIn(viewModelScope)

        sharedUseCases.observeFollowSystemModeUseCase.invoke().onEach {
            state = state.copy(followSystemMode = it)
        }
    }
}

data class SettingsState(
    val mode: Int = MODE_LIGHT,
    val followSystemMode: Boolean = false,
    val nativeLanguage: Int = LANGUAGE_RU
)

sealed class SettingsEvent {
    object OnBackClick : SettingsEvent()
    data class OnModeChanged(val mode: Int) : SettingsEvent()
    data class OnFollowSystemModeChanged(val follow: Boolean) : SettingsEvent()
    data class OnNativeLanguageChanged(val language: Int) : SettingsEvent()
}