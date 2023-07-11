package space.rodionov.porosenokpetr.main.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.domain.use_case.CollectIsFollowingSystemModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectNativeLanguageUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateModeUseCase
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.core.util.Language
import javax.inject.Inject

class RootViewModel @Inject constructor(
    collectModeUseCase: CollectModeUseCase,
    private val updateModeUseCase: UpdateModeUseCase,
    collectIsFollowingSystemModeUseCase: CollectIsFollowingSystemModeUseCase,
    collectNativeLanguageUseCase: CollectNativeLanguageUseCase
) : ViewModel() {

    var state by mutableStateOf(State())
        private set

    //==========================MODE=========================================
    private val _mode = collectModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun updateMode(mode: Int) = viewModelScope.launch {
        updateModeUseCase.invoke(mode)
    }

    //==========================FOLLOW SYSTEM MODE=========================================
    private val _followSystemMode = collectIsFollowingSystemModeUseCase.invoke()
    val followSystemMode = _followSystemMode.stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
        collectModeUseCase.invoke().onEach {
            state = state.copy(isDarkTheme = it)
        }.launchIn(viewModelScope)
        collectIsFollowingSystemModeUseCase.invoke().onEach {
            state = state.copy(isFollowSystemMode = it)
        }.launchIn(viewModelScope)
        collectNativeLanguageUseCase.invoke().onEach {
            state = state.copy(nativeLanguage = it)
        }.launchIn(viewModelScope)
    }

    data class State(
        val isDarkTheme: Int = MODE_LIGHT,
        val isFollowSystemMode: Boolean = false,
        val nativeLanguage: Language = Language.Russian
    )
}