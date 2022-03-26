package space.rodionov.porosenokpetr.feature_driller.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
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

}