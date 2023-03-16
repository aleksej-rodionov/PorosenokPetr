package space.rodionov.porosenokpetr.feature_splash.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_splash.domain.use_case.SplashInteractor
import javax.inject.Inject

class SplashCustomViewModel @Inject constructor(
    private val splashInteractor: SplashInteractor
) : ViewModel() {

    private val _isDbPopulated = Channel<Boolean>()
    val isDbPopulated = _isDbPopulated.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (splashInteractor.getWordQuantity() == 0) {

                splashInteractor.populateDatabase().onEach {
                    if (it) {
                        _isDbPopulated.send(true)
                    } else {
                        Log.d("TAG_DB", "DB not populated")
                    }
                }.launchIn(viewModelScope)

            } else {
                delay(1000L) // crunch to see so beautiful splash
                _isDbPopulated.send(true)
            }
        }
    }
}