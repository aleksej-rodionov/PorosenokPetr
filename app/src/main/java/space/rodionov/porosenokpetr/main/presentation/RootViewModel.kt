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
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateModeUseCase
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import javax.inject.Inject

class RootViewModel @Inject constructor(
    private val collectModeUseCase: CollectModeUseCase,
    private val updateModeUseCase: UpdateModeUseCase,
    private val collectIsFollowingSystemModeUseCase: CollectIsFollowingSystemModeUseCase,
//    private val splashInteractor: SplashInteractor
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
        _mode.onEach { state = state.copy(isDarkTheme = it) }.launchIn(viewModelScope)
        _followSystemMode.onEach { state = state.copy(isFollowSystemMode = it) }.launchIn(viewModelScope)
    }

//    fun onBtnClick() {
//        viewModelScope.launch {
//            Log.d("TAG_DB", "clicked")
//            val number = splashInteractor.getWordQuantity()
//            Log.d("TAG_DB", "number of words = ${number.toString()}")
//
////            val tenWords = repo.getTenWords()
////            tenWords.forEach {
////                Log.d("TAG_DB", "${it.swe}")
////            }
//        }
//    }

    data class State(
        val isDarkTheme: Int = MODE_LIGHT,
        val isFollowSystemMode: Boolean = false
    )
}