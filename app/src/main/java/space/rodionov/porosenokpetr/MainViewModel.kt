package space.rodionov.porosenokpetr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.Constants.FOLLOW_SYSTEM_MODE
import space.rodionov.porosenokpetr.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: WordRepo // todo сделать юзКейсы?
): ViewModel() {


    //==========================TRANSLATION DIRECTION=========================================
    private val _transDir = repo.getTransDir()
    val transDir = _transDir.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun updateTransDir(nativeToForeign: Boolean) = viewModelScope.launch {
        repo.setTransDir(nativeToForeign)
    }

    //==========================MODE=========================================
    private val _mode = MutableStateFlow(MODE_LIGHT)
    val mode = _mode.asStateFlow()

    fun getMode(): Int {
        val mode = repo.getMode()
        _mode.value = mode
        return mode
    }
    fun saveMode(mode: Int) {
        repo.setMode(mode)
        getMode()
    }

    //==========================FOLLOW SYSTEM MODE=========================================
    private val _followSystemMode = MutableStateFlow(false)
    val followSystemMode = _followSystemMode.asStateFlow()

    fun getFollowSystemMode(): Boolean {
        val follow = repo.getFollowSystemMode()
        _followSystemMode.value = follow
        return follow
    }
    fun saveFollowSystemMode(follow: Boolean) {
        repo.setFollowSystemMode(follow)
        getFollowSystemMode()
    }
}