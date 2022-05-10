package space.rodionov.porosenokpetr.feature_driller.di

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class AssistedViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = viewModels[modelClass]
            ?: throw IllegalArgumentException("model class $modelClass not found")
        return viewModelProvider.get() as T
    }

}

class SavedStateViewModelFactory @AssistedInject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>,
    @Assisted owner: SavedStateRegistryOwner,
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
//        CharactersViewModel(getCharactersUseCase, storageImpl, handle) as T
        val viewModelProvider = viewModels[modelClass]
            ?: throw IllegalArgumentException("model class $modelClass not found")
        return viewModelProvider.get() as T
    }
}

@AssistedFactory
interface SavedStateViewModelAssistedFactory {
    fun create(owner: SavedStateRegistryOwner): SavedStateViewModelFactory
}