package space.rodionov.porosenokpetr.feature_settings.presentation

import android.util.Log
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
import org.joda.time.LocalTime
import space.rodionov.porosenokpetr.core.domain.use_case.CheckIfAlarmSetUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectAvailableNativeLanguagesUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectIsFollowingSystemModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectNativeLanguageUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectTranslationDirectionUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateModeUseCase
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.core.util.Constants.TAG_PETR
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.CancelAlarmUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.EnableNextAlarmUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.GetIsReminderOnUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.GetReminderTimeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SetIsReminderOnUseCase
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.SetReminderTimeUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.CollectInterfaceLanguageUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.SetInterfaceLocaleConfigUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateIsFollowingSystemModeUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateNativeLanguageUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateTranslationDirectionUseCase

private const val TAG = "SettingsViewModelTAGGY"

class SettingsViewModel(
    private val collectModeUseCase: CollectModeUseCase,
    private val collectIsFollowingSystemModeUseCase: CollectIsFollowingSystemModeUseCase,
    private val collectNativeLanguageUseCase: CollectNativeLanguageUseCase,
    private val collectInterfaceLanguageUseCase: CollectInterfaceLanguageUseCase,
    private val collectTranslationDirectionUseCase: CollectTranslationDirectionUseCase,
    private val collectAvailableNativeLanguagesUseCase: CollectAvailableNativeLanguagesUseCase,
    private val updateModeUseCase: UpdateModeUseCase,
    private val updateIsFollowingSystemModeUseCase: UpdateIsFollowingSystemModeUseCase,
    private val updateNativeLanguageUseCase: UpdateNativeLanguageUseCase,
    private val setInterfaceLocaleConfigUseCase: SetInterfaceLocaleConfigUseCase,
    private val updateTranslationDirectionUseCase: UpdateTranslationDirectionUseCase,
    private val checkIfAlarmSetUseCase: CheckIfAlarmSetUseCase,
    private val enableNextAlarmUseCase: EnableNextAlarmUseCase,
    private val cancelAlarmUseCase: CancelAlarmUseCase,
    private val setIsReminderOnUseCase: SetIsReminderOnUseCase,
    private val getIsReminderOnUseCase: GetIsReminderOnUseCase,
    private val setReminderTimeUseCase: SetReminderTimeUseCase,
    private val getReminderTimeUseCase: GetReminderTimeUseCase,
) : ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    private val _uiEffect = Channel<UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        collectModeUseCase.invoke().onEach {
            state = state.copy(mode = it)
        }.launchIn(viewModelScope)

        collectIsFollowingSystemModeUseCase.invoke().onEach {
            state = state.copy(isFollowingSystemMode = it)
        }.launchIn(viewModelScope)

        collectNativeLanguageUseCase.invoke().onEach {
            state = state.copy(nativeLanguage = it)
        }.launchIn(viewModelScope)

        collectInterfaceLanguageUseCase.invoke().onEach {
            Log.d(TAG_PETR, "Interface language in keyValueStorage == $it")
            //todo сделать отдельный свитч?
        }.launchIn(viewModelScope)

        collectTranslationDirectionUseCase.invoke().onEach {
            state = state.copy(isNativeToForeign = it)
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            state = state.copy(
                availableNativeLanguages = collectAvailableNativeLanguagesUseCase.invoke().first()
            )
        }

        checkIfReminderSet()

        refreshInitialTimePickerValue()
    }

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
                    setInterfaceLocaleConfigUseCase.invoke(event.language.languageTag)
                }
            }

            is SettingsEvent.OnTranslationDirectionChanged -> {
                viewModelScope.launch {
                    updateTranslationDirectionUseCase.invoke(event.nativeToForeign)
                }
            }

            is SettingsEvent.OnIsReminderOnChanged -> {
                if (event.isSwitchedOn) {
                    setIsReminderOnUseCase.invoke(true)
                    enableNextAlarmUseCase.invoke()
                    state = state.copy(isReminderSet = true)
                } else {
                    setIsReminderOnUseCase.invoke(false)
                    cancelAlarmUseCase.invoke()
                    state = state.copy(isReminderSet = false)
                }
            }

            is SettingsEvent.OnOpenTimePickerClick -> {
                Log.d(TAG, "onEvent: oldReminderTime = ${getReminderTimeUseCase.invoke()}")
                state = state.copy(isTimePickerOpen = true)
            }

            is SettingsEvent.OnCloseTimePickerClick -> {
                Log.d(TAG, "onEvent: OnCloseTimePickerClick")
                state = state.copy(isTimePickerOpen = false)
            }

            is SettingsEvent.OnTimeChosen -> {
                setReminderTimeUseCase.invoke(event.hourOfDay, event.minuteOfHour)
                refreshInitialTimePickerValue()
                val newReminderTime = getReminderTimeUseCase.invoke()
                Log.d(TAG, "onEvent: newReminderTime = $newReminderTime")
                if (getIsReminderOnUseCase.invoke()) {
                    enableNextAlarmUseCase.invoke()
                }
            }
        }
    }

    private fun checkIfReminderSet() { //todo trigger after switching?
        val isSet = getIsReminderOnUseCase.invoke()
        state = state.copy(isReminderSet = isSet)
    }

    private fun refreshInitialTimePickerValue() {
        state = state.copy(initialTimePickerValue = getReminderTimeUseCase.invoke())
    }
}

data class SettingsState(
    val mode: Int = MODE_LIGHT,
    val isFollowingSystemMode: Boolean = false,
    val nativeLanguage: Language = Language.Russian,
    val isNativeToForeign: Boolean = false,
    val availableNativeLanguages: List<Language> = emptyList(),
    val isReminderSet: Boolean = false,
    val isTimePickerOpen: Boolean = false,
    val initialTimePickerValue: LocalTime = LocalTime(20, 0)
)

sealed class SettingsEvent {
    object OnBackClick : SettingsEvent()
    data class OnModeChanged(val mode: Int) : SettingsEvent()
    data class OnFollowSystemModeChanged(val follow: Boolean) : SettingsEvent()
    data class OnNativeLanguageChanged(val language: Language) : SettingsEvent()
    data class OnTranslationDirectionChanged(val nativeToForeign: Boolean) : SettingsEvent()
    data class OnIsReminderOnChanged(val isSwitchedOn: Boolean) : SettingsEvent()
    object OnOpenTimePickerClick : SettingsEvent()
    object OnCloseTimePickerClick : SettingsEvent()
    data class OnTimeChosen(val hourOfDay: Int, val minuteOfHour: Int) : SettingsEvent()
}