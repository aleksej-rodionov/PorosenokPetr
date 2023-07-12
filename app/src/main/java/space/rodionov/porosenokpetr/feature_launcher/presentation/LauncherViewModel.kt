package space.rodionov.porosenokpetr.feature_launcher.presentation

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
import space.rodionov.porosenokpetr.feature_launcher.domain.use_case.LauncherInteractor
import javax.inject.Inject

class LauncherViewModel @Inject constructor(
    private val launcherInteractor: LauncherInteractor
) : ViewModel() {

    private val _isDbPopulated = Channel<Boolean>()
    val isDbPopulated = _isDbPopulated.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (launcherInteractor.getWordQuantity() == 0) {

                launcherInteractor.populateDatabase().onEach {
                    if (it) {
                        _isDbPopulated.send(true)
                    } else {
                        Log.d("TAG_DB", "DB not populated")
                        _isDbPopulated.send(false)
                    }
                }.launchIn(viewModelScope)

            } else {
                delay(1000L) // crunch to see so beautiful splash
                _isDbPopulated.send(true)
            }
        }
    }
}