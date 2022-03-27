package space.rodionov.porosenokpetr.feature_driller.presentation.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.feature_driller.domain.models.MenuSwitch
import space.rodionov.porosenokpetr.feature_driller.domain.models.MenuSwitchWithTimePicker
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_DARK
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_SETTINGS
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsSwitchType
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val observeTransDirUseCase: ObserveTranslationDirectionUseCase,
    private val saveTransDirUseCase: SaveTranslationDirectionUseCase,
    private val observeModeUseCase: ObserveModeUseCase,
    private val setModeUseCase: SetModeUseCase,
    private val observeFollowSystemModeUseCase: ObserveFollowSystemModeUseCase,
    private val setFollowSystemModeUseCase: SetFollowSystemModeUseCase,
    private val observeReminderUseCase: ObserveReminderUseCase,
    private val setReminderUseCase: SetReminderUseCase,
    private val observeNotificationMillisUseCase: ObserveNotificationMillisUseCase,
    private val setNotificationMillisUseCase: SetNotificationMillisUseCase
) : ViewModel() {

    val inititalMenuList: MutableList<BaseModel> = SettingsHelper.getSettingsMenu()
    private val _menuListFlow = MutableStateFlow(inititalMenuList)
    val menuListFlow = _menuListFlow.asStateFlow()

    fun updateMenuList(type: SettingsSwitchType, state: Boolean) = viewModelScope.launch {
        val newList = mutableListOf<BaseModel>()
        menuListFlow.value.forEach { menuItem ->
            if (menuItem is MenuSwitch && menuItem.type == type) {
                val newItem = menuItem.copy(switchState = state)
                newList.add(newItem)
            } else if (menuItem is MenuSwitchWithTimePicker && menuItem.type == type) {
                val newItem = menuItem.copy(switchState = state)
                newList.add(newItem)
            } else {
                newList.add(menuItem)
            }
        }
        _menuListFlow.value = newList.toMutableList()
        if (type == SettingsSwitchType.SYSTEM_MODE) updateBlockedDarkModeItem(state)
    }

    private fun updateBlockedDarkModeItem(block: Boolean) = viewModelScope.launch {
        Log.d(TAG_SETTINGS, "updateBlockedDarkModeItem: block = $block")
        val newList = mutableListOf<BaseModel>()
        menuListFlow.value.forEach { menuItem ->
            if (menuItem is MenuSwitch && menuItem.type == SettingsSwitchType.NIGHT_MODE) {
                val newItem = menuItem.copy(isBlocked = block)
                newList.add(newItem)
            } else newList.add(menuItem)
        }
        _menuListFlow.value = newList.toMutableList()
    }

    fun updateNotificationTimeInList(millis: Long) = viewModelScope.launch {
        val newList = mutableListOf<BaseModel>()
        menuListFlow.value.forEach { menuItem ->
            if (menuItem is MenuSwitchWithTimePicker) {
                val newItem = menuItem.copy(millisSinceDayBeginning = millis)
                newList.add(newItem)
            } else newList.add(menuItem)
        }
        _menuListFlow.value = newList.toMutableList()
    }

    //==========================TRANSLATION DIRECTION=========================================
    private val _transDir = observeTransDirUseCase.invoke()
    val transDir = _transDir.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun updateTransDir(nativeToForeign: Boolean) = viewModelScope.launch {
        saveTransDirUseCase.invoke(nativeToForeign)
    }

    //==========================MODE=========================================
    private val _mode = observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun updateMode(mode:Int) = viewModelScope.launch {
        setModeUseCase.invoke(mode)
    }

    //==========================FOLLOW SYSTEM MODE=========================================
    private val _followSystemMode = observeFollowSystemModeUseCase.invoke()
    val followSystemMode = _followSystemMode.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun updateFollowSystemMode(follow: Boolean) = viewModelScope.launch {
        setFollowSystemModeUseCase.invoke(follow)
    }

    //==========================NOTIFICATION=========================================
    private val _remind = observeReminderUseCase.invoke()
    val remind = _remind.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun updateRemind(follow: Boolean) = viewModelScope.launch {
        setReminderUseCase.invoke(follow)
    }

    private val _notificationTime = observeNotificationMillisUseCase.invoke()
    val notificationTime = _notificationTime.stateIn(viewModelScope, SharingStarted.Lazily, Constants.MILLIS_IN_NINE_HOURS)

    fun updateNotificationTime(millis: Long) = viewModelScope.launch {
        setNotificationMillisUseCase.invoke(millis)
    }

    //=======================EVENT SHARED FLOW======================================
    private val _settingsEventFlow = MutableSharedFlow<SettingsEvent>()
    val settingsEventFlow = _settingsEventFlow.asSharedFlow()

    sealed class SettingsEvent {
        object OpenTimePicker : SettingsEvent()
    }

    //==============================METHODS==================================
    fun checkSwitch(type: SettingsSwitchType, isChecked: Boolean) = viewModelScope.launch {
        when (type) {
            SettingsSwitchType.TRANSLATION_DIRECTION -> updateTransDir(isChecked)
            SettingsSwitchType.NIGHT_MODE -> {
                updateMode(if (isChecked) MODE_DARK else MODE_LIGHT)
            }
            SettingsSwitchType.SYSTEM_MODE -> updateFollowSystemMode(isChecked)
            SettingsSwitchType.REMINDER -> updateRemind(isChecked)
        }
    }

    fun openTimePicker() = viewModelScope.launch {
        _settingsEventFlow.emit(SettingsEvent.OpenTimePicker)
    }
}