package space.rodionov.porosenokpetr

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: WordRepo // todo сделать юзКейсы?
): ViewModel() {

    private val _isNightMainViewModel = MutableStateFlow(false)
    val isNightMainViewModel = _isNightMainViewModel.asStateFlow()

    fun saveMode(isNight: Boolean) {
        repo.setMode(isNight)
        getMode()
    }

    fun saveMode() {
        repo.setMode(!repo.getMode())
        getMode()
    }

    fun getMode(): Boolean {
        val night = repo.getMode()
        _isNightMainViewModel.value = night
        return night
    }
}