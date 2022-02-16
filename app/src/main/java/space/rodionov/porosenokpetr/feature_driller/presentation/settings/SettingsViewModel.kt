package space.rodionov.porosenokpetr.feature_driller.presentation.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import space.rodionov.porosenokpetr.feature_driller.data.storage.Datastore
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: WordRepo
) : ViewModel() {

//    private val _transDir = prefs.
}