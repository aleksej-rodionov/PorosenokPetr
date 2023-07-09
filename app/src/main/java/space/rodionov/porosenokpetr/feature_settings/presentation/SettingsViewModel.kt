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
import space.rodionov.porosenokpetr.core.domain.use_case.CollectIsFollowingSystemModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectNativeLanguageUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateModeUseCase
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateIsFollowingSystemModeUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateNativeLanguageUseCase

class SettingsViewModel(
    private val updateModeUseCase: UpdateModeUseCase,
    private val updateIsFollowingSystemModeUseCase: UpdateIsFollowingSystemModeUseCase,
    private val collectModeUseCase: CollectModeUseCase,
    private val collectIsFollowingSystemModeUseCase: CollectIsFollowingSystemModeUseCase,
    private val collectNativeLanguageUseCase: CollectNativeLanguageUseCase,
    private val updateNativeLanguageUseCase: UpdateNativeLanguageUseCase
) : ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    private val _uiEffect = Channel<UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnBackClick -> {
                viewModelScope.launch { _uiEffect.send(UiEffect.NavigateUp) }
            }

            is SettingsEvent.OnModeChanged -> {
                viewModelScope.launch { updateModeUseCase.invoke(event.mode) }
            }

            is SettingsEvent.OnFollowSystemModeChanged -> {
                viewModelScope.launch { updateIsFollowingSystemModeUseCase.invoke(event.follow) }
            }

            is SettingsEvent.OnNativeLanguageChanged -> {
                viewModelScope.launch {
                    updateNativeLanguageUseCase.invoke(event.language)
                }
            }
        }
    }

    init {
        collectModeUseCase.invoke().onEach {
            state = state.copy(mode = it)
        }.launchIn(viewModelScope)

        collectIsFollowingSystemModeUseCase.invoke().onEach {
            state = state.copy(followSystemMode = it)
        }.launchIn(viewModelScope)

        collectNativeLanguageUseCase.invoke().onEach {
            //todo update state
        }.launchIn(viewModelScope)
    }
}

data class SettingsState(
    val mode: Int = MODE_LIGHT,
    val followSystemMode: Boolean = false,
    val nativeLanguage: Language = Language.Russian
)

sealed class SettingsEvent {
    object OnBackClick : SettingsEvent()
    data class OnModeChanged(val mode: Int) : SettingsEvent()
    data class OnFollowSystemModeChanged(val follow: Boolean) : SettingsEvent()
    data class OnNativeLanguageChanged(val language: Language) : SettingsEvent()
}