package space.rodionov.porosenokpetr.feature_driller.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.domain.models.BaseModel
import space.rodionov.porosenokpetr.feature_driller.domain.models.MenuLanguage
import space.rodionov.porosenokpetr.feature_driller.domain.models.MenuSwitch
import space.rodionov.porosenokpetr.feature_driller.domain.models.MenuSwitchWithTimePicker
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.feature_driller.presentation.settings.language.LanguageItem
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.FOREIGN_LANGUAGE_CHANGE
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_DARK
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_CHANGE
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_RU
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_UA
import space.rodionov.porosenokpetr.feature_driller.utils.SettingsItemType
import space.rodionov.porosenokpetr.feature_driller.work.NotificationHelper
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val notificationHelper: NotificationHelper,
    private val observeTransDirUseCase: ObserveTranslationDirectionUseCase,
    private val saveTransDirUseCase: SaveTranslationDirectionUseCase,
    private val observeModeUseCase: ObserveModeUseCase,
    private val setModeUseCase: SetModeUseCase,
    private val observeFollowSystemModeUseCase: ObserveFollowSystemModeUseCase,
    private val setFollowSystemModeUseCase: SetFollowSystemModeUseCase,
    private val observeNativeLangUseCase: ObserveNativeLangUseCase,
    private val updateNativeLangUseCase: UpdateNativeLangUseCase,
    private val observeLearnedLangUseCase: ObserveLearnedLangUseCase,
    private val updateLearnedLangUseCase: UpdateLearnedLangUseCase,
    private val observeReminderUseCase: ObserveReminderUseCase,
    private val setReminderUseCase: SetReminderUseCase,
    private val observeNotificationMillisUseCase: ObserveNotificationMillisUseCase,
    private val setNotificationMillisUseCase: SetNotificationMillisUseCase
) : ViewModel() {

    var justOpened = true

    val inititalMenuList: MutableList<BaseModel> = SettingsHelper.getSettingsMenu()
    private val _menuListFlow = MutableStateFlow(inititalMenuList)
    val menuListFlow = _menuListFlow.asStateFlow()

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

    private fun updateFollowSystemMode(follow: Boolean) = viewModelScope.launch {
        setFollowSystemModeUseCase.invoke(follow)
    }

    //==========================NATIVE LANGUAGE=========================================
    private val _nativeLanguage = observeNativeLangUseCase.invoke()
    val nativeLanguage = _nativeLanguage.stateIn(viewModelScope, SharingStarted.Lazily,
        Constants.LANGUAGE_RU
    )

    private fun updateNativeLanguage(lang: Int) = viewModelScope.launch {
        updateNativeLangUseCase.invoke(lang)
    }

    //==========================LEARNED LANGUAGE=========================================
    private val _learnedLanguage = observeLearnedLangUseCase.invoke()
    val learnedLanguage = _learnedLanguage.stateIn(viewModelScope, SharingStarted.Lazily,
        Constants.LANGUAGE_EN
    )

    private fun updateLearnedLanguage(lang: Int) = viewModelScope.launch {
        updateLearnedLangUseCase.invoke(lang)
    }

    //==========================NOTIFICATION=========================================
    private val _remind = observeReminderUseCase.invoke()
    val remind = _remind.stateIn(viewModelScope, SharingStarted.Lazily, false)

    private fun updateRemind(follow: Boolean) = viewModelScope.launch {
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
        data class OnChangeLang(val nativeOrForeign: Int) : SettingsEvent()
        data class ShowSnackbar(val text: String) : SettingsEvent()
    }

    //==============================METHODS==================================

    init {
        notJustOpened()
    }

    fun updateMenuList(type: SettingsItemType, state: Boolean) = viewModelScope.launch {
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
        if (type == SettingsItemType.SYSTEM_MODE) updateBlockedDarkModeItem(state)
    }

    fun updateMenuItemsInList(type: SettingsItemType, lang: LanguageItem) = viewModelScope.launch {
        val newList = mutableListOf<BaseModel>()
        menuListFlow.value.forEach { menuItem ->
            if (menuItem is MenuLanguage && menuItem.type == type) {
                val newItem = menuItem.copy(language = lang)
                newList.add(newItem)
            } else {
                newList.add(menuItem)
            }
        }
        _menuListFlow.value = newList.toMutableList()
    }

    private fun updateBlockedDarkModeItem(block: Boolean) = viewModelScope.launch {
        val newList = mutableListOf<BaseModel>()
        menuListFlow.value.forEach { menuItem ->
            if (menuItem is MenuSwitch && menuItem.type == SettingsItemType.NIGHT_MODE) {
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

    fun checkSwitch(type: SettingsItemType, isChecked: Boolean) = viewModelScope.launch {
        when (type) {
            SettingsItemType.TRANSLATION_DIRECTION -> updateTransDir(isChecked)
            SettingsItemType.NIGHT_MODE -> {
                updateMode(if (isChecked) MODE_DARK else MODE_LIGHT)
            }
            SettingsItemType.SYSTEM_MODE -> updateFollowSystemMode(isChecked)
            SettingsItemType.REMINDER -> updateRemind(isChecked)
            SettingsItemType.NATIVE_LANG -> {
                updateNativeLanguage(if (isChecked) LANGUAGE_UA else LANGUAGE_RU)
            }
        }
    }

    fun openTimePicker() = viewModelScope.launch {
        _settingsEventFlow.emit(SettingsEvent.OpenTimePicker)
    }

    fun onChangeLang(type: SettingsItemType) = viewModelScope.launch {
        when (type) {
            SettingsItemType.CHANGE_NATIVE_LANG ->
                _settingsEventFlow.emit(SettingsEvent.OnChangeLang(NATIVE_LANGUAGE_CHANGE))
            SettingsItemType.CHANGE_LEARNED_LANG ->
                _settingsEventFlow.emit(SettingsEvent.OnChangeLang(FOREIGN_LANGUAGE_CHANGE))
        }
    }

    fun showSnackbar(text: String) = viewModelScope.launch {
        _settingsEventFlow.emit(SettingsEvent.ShowSnackbar(text))
    }

    fun buildAndScheduleNotification() = notificationHelper.buildNotification(notificationTime.value)
    fun buildAndScheduleNotification(millisFromDayStart: Long) = notificationHelper.buildNotification(millisFromDayStart)
    fun cancelNotification() = notificationHelper.cancelNotification()

    fun scheduleSuccessSnackBar(notificationTime: Long, titleNotificationSchedule: String, patternNotificationSchedule: String) {
        showSnackbar(titleNotificationSchedule + SimpleDateFormat(
            patternNotificationSchedule, Locale.getDefault()
        ).format(notificationTime).toString())
//        showSnackBar(
//            Constants.DEFAULT_INT, titleNotificationSchedule + SimpleDateFormat(
//            patternNotificationSchedule, Locale.getDefault()
//        ).format(notificationTime).toString())
    }

    fun scheduleErrorSnackbar(text: String) {
        showSnackbar(text)
//        showSnackBar(R.string.notification_schedule_error, "")
    }

    fun notJustOpened() = viewModelScope.launch { // его величество костыль
        delay(500L)
        justOpened = false
    }
}