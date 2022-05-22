package space.rodionov.porosenokpetr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.DrillerUseCases
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_RU
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewModel @Inject constructor(
    private val drillerUseCases: DrillerUseCases
): ViewModel() {
//    private val _showFragments = MutableStateFlow<Boolean>(true)
//    val showFragments = _showFragments.asStateFlow()
//
//    fun switchUiShit() = viewModelScope.launch {
//        _showFragments.value = !showFragments.value
//    }

    //==========================MODE=========================================
    private val _mode = drillerUseCases.observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun updateMode(mode:Int) = viewModelScope.launch {
        drillerUseCases.setModeUseCase.invoke(mode)
    }

    //==========================FOLLOW SYSTEM MODE=========================================
    private val _followSystemMode = drillerUseCases.observeFollowSystemModeUseCase.invoke()
    val followSystemMode = _followSystemMode.stateIn(viewModelScope, SharingStarted.Lazily, false)

    //==========================LANGUAGE=========================================
    private val _nativeLanguage = drillerUseCases.observeNativeLangUseCase.invoke()
    val nativeLanguage = _nativeLanguage.stateIn(viewModelScope, SharingStarted.Lazily, LANGUAGE_RU)

    //==========================REMINDER=========================================
    private val _reminder = drillerUseCases.observeReminderUseCase.invoke()
    val reminder = _reminder.stateIn(viewModelScope, SharingStarted.Lazily, false)

    //=============================METHODS======================================
}