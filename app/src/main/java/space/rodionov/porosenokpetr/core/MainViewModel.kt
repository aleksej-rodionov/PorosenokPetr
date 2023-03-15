package space.rodionov.porosenokpetr.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.domain.use_case.SharedUseCases
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.CardStackUseCases
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewModel @Inject constructor(
    private val sharedUseCases: SharedUseCases,
    private val cardStackUseCases: CardStackUseCases,

    private val repo: WordRepo//todo remove
) : ViewModel() {

    //==========================MODE=========================================
    private val _mode = sharedUseCases.observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun updateMode(mode: Int) = viewModelScope.launch {
        sharedUseCases.setModeUseCase.invoke(mode)
    }

    //==========================FOLLOW SYSTEM MODE=========================================
    private val _followSystemMode = sharedUseCases.observeFollowSystemModeUseCase.invoke()
    val followSystemMode = _followSystemMode.stateIn(viewModelScope, SharingStarted.Lazily, false)



    fun onBtnClick() {
        viewModelScope.launch {
//            Log.d("TAG_DB", "clicked")
//            val number = repo.getWordsQuantity()
//            Log.d("TAG_DB", "number of words = ${number.toString()}")

            val tenWords = repo.getTenWords()
//            Log.d("TAG_DB", "${tenWords}")
            tenWords.forEach {
                Log.d("TAG_DB", "${it.swe}")
            }
        }
    }
}