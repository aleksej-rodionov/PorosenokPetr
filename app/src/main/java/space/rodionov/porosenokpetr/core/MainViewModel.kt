package space.rodionov.porosenokpetr.core

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
    private val sharedUseCases: SharedUseCases
): ViewModel() {

    //==========================MODE=========================================
    private val _mode = sharedUseCases.observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun updateMode(mode:Int) = viewModelScope.launch {
        sharedUseCases.setModeUseCase.invoke(mode)
    }

    //==========================FOLLOW SYSTEM MODE=========================================
    private val _followSystemMode = sharedUseCases.observeFollowSystemModeUseCase.invoke()
    val followSystemMode = _followSystemMode.stateIn(viewModelScope, SharingStarted.Lazily, false)
}