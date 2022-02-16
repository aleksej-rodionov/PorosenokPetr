package space.rodionov.porosenokpetr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import space.rodionov.porosenokpetr.Constants.FOLLOW_SYSTEM_MODE
import space.rodionov.porosenokpetr.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: WordRepo // todo сделать юзКейсы?
): ViewModel() {

    private val _mode = MutableStateFlow(MODE_LIGHT)
    val mode = _mode.asStateFlow()

    private val _followSystemMode = MutableStateFlow(false)
    val followSystemMode = _followSystemMode.asStateFlow()

    fun saveMode(mode: Int) {
        repo.setMode(mode)
        getMode()
    }

    fun getMode(): Int {
        val mode = repo.getMode()
        _mode.value = mode
        return mode
    }

    fun saveFollowSystemMode(follow: Boolean) {
        repo.setFollowSystemMode(follow)
        getFollowSystemMode()
    }

    fun getFollowSystemMode(): Boolean {
        val follow = repo.getFollowSystemMode()
        _followSystemMode.value = follow
        return follow
    }
}