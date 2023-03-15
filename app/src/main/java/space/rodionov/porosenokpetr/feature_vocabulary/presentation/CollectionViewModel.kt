package space.rodionov.porosenokpetr.feature_vocabulary.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.util.ViewModelAssistedFactory
import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.use_case.SharedUseCases
import space.rodionov.porosenokpetr.core.util.Constants
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import javax.inject.Inject

class CollectionViewModel (
    private val sharedUseCases: SharedUseCases,
    private val preferenvesUseCases: PreferencesUseCases,
    private val state: SavedStateHandle,
) : ViewModel() {
    private var activeCatsAmount = state.get<Int>("activeCatsAmount") ?: 0
        set(value) {
            field = value
            state.set("activeCatsAmount", value)
        }

    private val _mode = preferenvesUseCases.observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, MODE_LIGHT)

    private val _categories = sharedUseCases.observeAllCatsWithWordsUseCase.invoke()
    val categories = _categories.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _activeCatsFlow = sharedUseCases.observeAllActiveCatsNamesUseCase.invoke()
    val activeCatsFlow = _activeCatsFlow.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _eventFlow = MutableSharedFlow<CollectionEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _nativeLanguage = sharedUseCases.observeNativeLangUseCase.invoke()
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
        sharedUseCases.makeCategoryActiveUseCase(catName, true)
    }

    fun inactivateCategory(catName: String) = viewModelScope.launch  {
        sharedUseCases.makeCategoryActiveUseCase(catName, false)
    }
}

class CollectionViewModelFactory @Inject constructor(
    private val sharedUseCases: SharedUseCases,
    private val preferenvesUseCases: PreferencesUseCases
    ) : ViewModelAssistedFactory<CollectionViewModel> {
    override fun create(handle: SavedStateHandle): CollectionViewModel {
        return CollectionViewModel(sharedUseCases, preferenvesUseCases, handle)
    }
}

//class CollectionViewModelFactory @AssistedInject constructor(
//    private val drillerUseCases: DrillerUseCases,
//    @Assisted owner: SavedStateRegistryOwner,
//) : AbstractSavedStateViewModelFactory(owner, null) {
//    override fun <T : ViewModel?> create(
//        key: String,
//        modelClass: Class<T>,
//        handle: SavedStateHandle
//    ): T = CollectionViewModel(drillerUseCases, handle) as T
//}
//
//@AssistedFactory
//interface CollectionViewModelAssistedFactory {
//    fun create(owner: SavedStateRegistryOwner): CollectionViewModelFactory
//}







