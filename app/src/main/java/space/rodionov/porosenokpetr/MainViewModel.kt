package space.rodionov.porosenokpetr

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.DrillerUseCases
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.ObserveWordsSearchQueryUseCase
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.LANGUAGE_RU
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewModel @Inject constructor(
    private val drillerUseCases: DrillerUseCases
): ViewModel() {

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

    val _topVerbs = drillerUseCases.observeWordsSearchQueryUseCase.invoke("Топ 200 глаголов", "")
    val topVerbs = _topVerbs.stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    init {
        viewModelScope.launch {
            delay(5000L)
            val json = Gson().toJson(topVerbs.value)
            Log.d("WORDS_JSON", json)
        }
    }
}