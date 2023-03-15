package space.rodionov.porosenokpetr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.domain.use_case.SharedUseCases
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_RU
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewModel @Inject constructor(
    private val sharedUseCases: SharedUseCases,
    private val preferencesUseCases: PreferencesUseCases
): ViewModel() {

    //==========================MODE=========================================
    private val _mode = preferencesUseCases.observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun updateMode(mode:Int) = viewModelScope.launch {
        preferencesUseCases.setModeUseCase.invoke(mode)
    }

    //==========================FOLLOW SYSTEM MODE=========================================
    private val _followSystemMode = preferencesUseCases.observeFollowSystemModeUseCase.invoke()
    val followSystemMode = _followSystemMode.stateIn(viewModelScope, SharingStarted.Lazily, false)

    //==========================LANGUAGE=========================================
    private val _nativeLanguage = sharedUseCases.observeNativeLangUseCase.invoke()
    val nativeLanguage = _nativeLanguage.stateIn(viewModelScope, SharingStarted.Lazily, LANGUAGE_RU)

    //=============================METHODS======================================
}