package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.domain.models.CatWithWords
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
   //todo private var activeCatsAmount = ssh.get<Int>("activeCatsAmount") ?: 0
    private val _state = mutableStateOf(CollectionState())
    val state: State<CollectionState> = _state

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
                    catsWithWords = cwws,
                    disableTurningOffCategories = areLessThanTwoActiveCategories(cwws)
                )
            }
            .launchIn(viewModelScope)
    }

    private fun areLessThanTwoActiveCategories(categories: List<CatWithWords>) : Boolean {
        return categories.filter {
            it.category.isCategoryActive
        }.size < 2
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

    private fun activateCategory(catName: String) = viewModelScope.launch {
        drillerUseCases.makeCategoryActiveUseCase(catName, true)
    }

    private fun inactivateCategory(catName: String) = viewModelScope.launch  {
        drillerUseCases.makeCategoryActiveUseCase(catName, false)
    }
}









