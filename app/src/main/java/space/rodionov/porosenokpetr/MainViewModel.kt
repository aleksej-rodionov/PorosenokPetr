package space.rodionov.porosenokpetr

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_RU
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_PETR
import space.rodionov.porosenokpetr.feature_driller.work.NotificationHelper
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val observeModeUseCase: ObserveModeUseCase,
    private val setModeUseCase: SetModeUseCase,
    private val observeNativeLangUseCase: ObserveNativeLangUseCase,
    private val updateNativeLangUseCase: UpdateNativeLangUseCase,
    private val observeFollowSystemModeUseCase: ObserveFollowSystemModeUseCase,
    private val observeFollowSystemLocaleUseCase: ObserveFollowSystemLocaleUseCase,
    private val observeReminderUseCase: ObserveReminderUseCase
): ViewModel() {

    //==========================MODE=========================================
    private val _mode = observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun updateMode(mode:Int) = viewModelScope.launch {
        setModeUseCase.invoke(mode)
    }

    //==========================FOLLOW SYSTEM MODE=========================================
    private val _followSystemMode = observeFollowSystemModeUseCase.invoke()
    val followSystemMode = _followSystemMode.stateIn(viewModelScope, SharingStarted.Lazily, false)

    //==========================LANGUAGE=========================================
    private val _nativeLanguage = observeNativeLangUseCase.invoke()
    val nativeLanguage = _nativeLanguage.stateIn(viewModelScope, SharingStarted.Lazily, NATIVE_LANGUAGE_RU)

    fun updateNativeLanguage(lang: Int) = viewModelScope.launch {
        updateNativeLangUseCase.invoke(lang)
    }

    //==========================FOLLOW SYSTEM LANGUAGE=========================================
    private val _followSystemLocale = observeFollowSystemLocaleUseCase.invoke()
    val followSystemLocale = _followSystemLocale.stateIn(viewModelScope, SharingStarted.Lazily, false)

    //==========================REMINDER=========================================
    private val _reminder = observeReminderUseCase.invoke()
    val reminder = _reminder.stateIn(viewModelScope, SharingStarted.Lazily, false)

    //=============================METHODS======================================
}