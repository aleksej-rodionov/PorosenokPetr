package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import javax.inject.Inject

class CollectionViewModel @Inject constructor(
    private val drillerUseCases: DrillerUseCases,
   private val state: SavedStateHandle
) : ViewModel() {
    private var activeCatsAmount = state.get<Int>("activeCatsAmount") ?: 0
        set(value) {
            field = value
            state.set("activeCatsAmount", value)
        }

    private val _mode = drillerUseCases.observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, MODE_LIGHT)

    private val _categories = drillerUseCases.observeAllCatsWithWordsUseCase.invoke()
    val categories = _categories.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _activeCatsFlow = drillerUseCases.observeAllActiveCatsNamesUseCase.invoke()
    val activeCatsFlow = _activeCatsFlow.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _eventFlow = MutableSharedFlow<CollectionEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _nativeLanguage = drillerUseCases.observeNativeLangUseCase.invoke()
    val nativeLanguage = _nativeLanguage.stateIn(viewModelScope, SharingStarted.Lazily,
        Constants.LANGUAGE_RU
    )

    sealed class CollectionEvent {
        data class NavigateToWordlistScreen(val cat: Category?) : CollectionEvent()
        data class RefreshCatSwitch(val cat: Category) : CollectionEvent()
        data class ShowSnackbar(val msg: String) : CollectionEvent()
    }

//============================METHODS=============================================================

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
        drillerUseCases.makeCategoryActiveUseCase(catName, true)
    }

    fun inactivateCategory(catName: String) = viewModelScope.launch  {
        drillerUseCases.makeCategoryActiveUseCase(catName, false)
    }
}

class CollectionViewModelFactory @AssistedInject constructor(
    private val drillerUseCases: DrillerUseCases,
    @Assisted owner: SavedStateRegistryOwner,
) : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = CollectionViewModel(drillerUseCases, handle) as T
}

@AssistedFactory
interface CollectionViewModelAssistedFactory {
    fun create(owner: SavedStateRegistryOwner): CollectionViewModelFactory
}







