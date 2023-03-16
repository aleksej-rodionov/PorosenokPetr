package space.rodionov.porosenokpetr.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.domain.use_case.SharedUseCases
import space.rodionov.porosenokpetr.feature_splash.domain.use_case.SplashInteractor
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.CardStackUseCases
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewModel @Inject constructor(
    private val sharedUseCases: SharedUseCases,
    private val cardStackUseCases: CardStackUseCases,
    private val splashInteractor: SplashInteractor
//    private val repo: WordRepo//todo remove
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

//    init {
//        Log.d("TAG_DB", ": SplashVM inited")
//        viewModelScope.launch(Dispatchers.IO) {
//            if (splashInteractor.getWordQuantity() == 0) {
//                splashInteractor.populateDatabase()
//            }
////            delay(1000L)
////            _isDbPopulated.send(true)
//        }
//    }

    fun onBtnClick() {
        viewModelScope.launch {
            Log.d("TAG_DB", "clicked")
            val number = splashInteractor.getWordQuantity()
            Log.d("TAG_DB", "number of words = ${number.toString()}")

//            val tenWords = repo.getTenWords()
//            tenWords.forEach {
//                Log.d("TAG_DB", "${it.swe}")
//            }
        }
    }
}