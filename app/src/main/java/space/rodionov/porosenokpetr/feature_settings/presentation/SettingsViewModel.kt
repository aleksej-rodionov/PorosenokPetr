package space.rodionov.porosenokpetr.feature_settings.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
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
): ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    private val _uiEffect = Channel<UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnBackClick -> {

            }
            is SettingsEvent.OnModeChanged -> {

            }
            is SettingsEvent.OnNativeLanguageChanged -> {

            }
        }
    }
}

data class SettingsState(
    val mode: Int = MODE_LIGHT,
    val nativeLanguage: Int = LANGUAGE_RU
)

sealed class SettingsEvent {
    object OnBackClick : SettingsEvent()
    data class OnModeChanged(val mode: Int): SettingsEvent()
    data class OnNativeLanguageChanged(val language: Int): SettingsEvent()
}