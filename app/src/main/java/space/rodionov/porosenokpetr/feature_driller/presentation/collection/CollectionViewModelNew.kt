package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.feature_driller.presentation.util.Screen
import space.rodionov.porosenokpetr.feature_driller.presentation.util.UiEvent
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import javax.inject.Inject

class CollectionViewModelNew @Inject constructor(
    private val drillerUseCases: DrillerUseCases,
    private val ssh: SavedStateHandle
) : ViewModel() {
    private var activeCatsAmount = ssh.get<Int>("activeCatsAmount") ?: 0
        set(value) {
            field = value
            ssh.set("activeCatsAmount", value)
        }

//    private val _mode = drillerUseCases.observeModeUseCase.invoke()
//    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, Constants.MODE_LIGHT)

//    private val _categories = observeAllCatsWithWordsUseCase.invoke()
//    val categories = _categories.stateIn(viewModelScope, SharingStarted.Lazily, null)
    private val _state = mutableStateOf(CollectionState())
    val state: State<CollectionState> = _state

//    private val _activeCatsFlow = drillerUseCases.observeAllActiveCatsNamesUseCase.invoke()
//    val activeCatsFlow = _activeCatsFlow.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var getCollectionJob: Job? = null
    private var getModeJob: Job? = null

    init {
        getCollection()
        getMode()
    }

//============================METHODS=============================================================

    private fun getCollection() {
        getCollectionJob?.cancel()
        getCollectionJob = drillerUseCases.observeAllCatsWithWordsUseCase()
            .onEach { cwws ->
                _state.value = state.value.copy(
                    catsWithWords = cwws
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getMode() {
        getModeJob?.cancel()
        getModeJob = drillerUseCases.observeModeUseCase()
            .onEach {
                _state.value = state.value.copy(
                    mode = it
                )
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: CollectionEvent) {
        when (event) {
            is CollectionEvent.onCategoryClick -> {
                sendUiEvent(UiEvent.Navigate(Screen.CollectionScreen.route + "?category=${event.cat.resourceName}"))
            }
            is CollectionEvent.onCategoryOnChange -> {
                if (event.isOn) {
                    activateCategory(event.cat.resourceName)
                } else {
                    inactivateCategory(event.cat.resourceName)
                }
            }
            is CollectionEvent.onSearchClick -> {
                sendUiEvent(UiEvent.Navigate(Screen.CollectionScreen.route))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

//    fun refreshActiveCatsAmount(newValue: Int) {
//        activeCatsAmount = newValue
//    }

//    fun howManyActiveCats() : Int = activeCatsAmount

    fun onCategoryClick(cat: Category) = viewModelScope.launch {
        _uiEvent.emit(UiEvent.NavigateToWordlistScreen(cat))
    }

    fun onSearchClick() = viewModelScope.launch {
        _uiEvent.emit(CollectionEvent.NavigateToWordlistScreen(null))
    }

//    fun updateCatSwitchState(cat: Category) = viewModelScope.launch {
//        _uiEvent.emit(CollectionEvent.RefreshCatSwitch(cat))
//    }

    fun showSnackbar(msg: String) = viewModelScope.launch {
        _uiEvent.emit(CollectionEvent.ShowSnackbar(msg))
    }

    fun activateCategory(catName: String) = viewModelScope.launch {
        drillerUseCases.makeCategoryActiveUseCase(catName, true)
    }

    fun inactivateCategory(catName: String) = viewModelScope.launch  {
        drillerUseCases.makeCategoryActiveUseCase(catName, false)
    }
}









