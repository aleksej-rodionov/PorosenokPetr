package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.MakeCategoryActiveUseCase
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.ObserveAllActiveCatsNamesUseCase
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.ObserveAllCatsWithWordsUseCase
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.ObserveModeUseCase
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import javax.inject.Inject

@HiltViewModel
class CollectionViewModelNew @Inject constructor(
    private val observeAllCatsWithWordsUseCase: ObserveAllCatsWithWordsUseCase,
    private val makeCategoryActiveUseCase: MakeCategoryActiveUseCase,
    private val observeAllActiveCatsNamesUseCase: ObserveAllActiveCatsNamesUseCase,
    private val observeMode: ObserveModeUseCase,
    private val ssh: SavedStateHandle
) : ViewModel() {
    private var activeCatsAmount = ssh.get<Int>("activeCatsAmount") ?: 0
        set(value) {
            field = value
            ssh.set("activeCatsAmount", value)
        }

    private val _mode = observeMode.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, Constants.MODE_LIGHT)

//    private val _categories = observeAllCatsWithWordsUseCase.invoke()
//    val categories = _categories.stateIn(viewModelScope, SharingStarted.Lazily, null)
    private val _state = mutableStateOf(CollectionState())
    val state: State<CollectionState> = _state

    private val _activeCatsFlow = observeAllActiveCatsNamesUseCase.invoke()
    val activeCatsFlow = _activeCatsFlow.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _eventFlow = MutableSharedFlow<CollectionEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class CollectionEvent {
        data class NavigateToWordlistScreen(val cat: Category?) : CollectionEvent()
        data class RefreshCatSwitch(val cat: Category) : CollectionEvent()
        data class ShowSnackbar(val msg: String) : CollectionEvent()
    }

    private var getCollectionJob: Job? = null

    init {
        getCollection()
    }

//============================METHODS=============================================================

    private fun getCollection() {
        getCollectionJob?.cancel()
        getCollectionJob = observeAllCatsWithWordsUseCase()
            .onEach { cwws ->
                _state.value = state.value.copy(
                    catsWithWords = cwws
                )
            }
            .launchIn(viewModelScope)
    }

    fun refreshActiveCatsAmount(newValue: Int) {
        activeCatsAmount = newValue
    }

    fun howManyActiveCats() : Int = activeCatsAmount

    fun onCategoryClick(cat: Category) = viewModelScope.launch {
        _eventFlow.emit(CollectionEvent.NavigateToWordlistScreen(cat))
    }

    fun onSearchClick() = viewModelScope.launch {
        _eventFlow.emit(CollectionEvent.NavigateToWordlistScreen(null))
    }

    fun updateCatSwitchState(cat: Category) = viewModelScope.launch {
        _eventFlow.emit(CollectionEvent.RefreshCatSwitch(cat))
    }

    fun shoeSnackbar(msg: String) = viewModelScope.launch {
        _eventFlow.emit(CollectionEvent.ShowSnackbar(msg))
    }

    fun activateCategory(catName: String) = viewModelScope.launch {
        makeCategoryActiveUseCase(catName, true)
    }

    fun inactivateCategory(catName: String) = viewModelScope.launch  {
        makeCategoryActiveUseCase(catName, false)
    }
}









