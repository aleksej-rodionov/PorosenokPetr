package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist.edit_add_word

import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.di.ApplicationScope
import space.rodionov.porosenokpetr.feature_driller.di.ViewModelAssistedFactory
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.feature_driller.presentation.driller.DrillerViewModel
import space.rodionov.porosenokpetr.feature_driller.presentation.wordlist.WordlistViewModel
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import javax.inject.Inject

class EditAddWordViewModel @Inject constructor(
    private val drillerUseCases: DrillerUseCases,
    private val state: SavedStateHandle,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {
    //    var nativeLangLiveData = state.getLiveData<Int?>("nativeLangLiveData", null)
    var nativLivedata = state.getLiveData<String>("nativLivedata", null)
    var foreignLivedata = state.getLiveData<String>("foreignLivedata", null)
    var catNameLivedata = state.getLiveData<String?>("catNameLivedata", null)

    private val _word = combine(
        nativLivedata.asFlow(),
        foreignLivedata.asFlow(),
        catNameLivedata.asFlow()
    ) { rus, eng, catName ->
        Triple(rus, eng, catName)
    }.flatMapLatest { (rus, eng, catName) ->
        drillerUseCases.observeWordUseCase.invoke(rus, eng, catName)
    }
    val word = _word.stateIn(viewModelScope, SharingStarted.Lazily, null)

    //====================================NATIVE LANG==================================
    private val _nativeLang = drillerUseCases.observeNativeLangUseCase.invoke()
    val nativeLang = _nativeLang.stateIn(viewModelScope, SharingStarted.Lazily, Constants.LANGUAGE_RU)

    //============MODE============
    private val _mode = drillerUseCases.observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, 0)



    //======================UI EVENTS=================================================
    private val _eventFlow = MutableSharedFlow<EditAddWOrdEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class EditAddWOrdEvent{
        object ShowInfo : EditAddWOrdEvent()
    }



    //=======================================METHODS========================================

    fun onDoneCLick(newWord: Word) = applicationScope.launch {
        word.value?.let {
            drillerUseCases.updateWordUseCase.invoke(it, newWord)
        }
    }

    fun activateWord() = viewModelScope.launch {
        nativLivedata.value?.let { nativ ->
            foreignLivedata.value?.let { foreign ->
                catNameLivedata.value?.let { catName ->
                    drillerUseCases.updateIsWordActiveUseCase.invoke(nativ, foreign, catName, true)
                }
            }
        }
    }

    fun inactivateWord() = viewModelScope.launch {
        nativLivedata.value?.let { nativ ->
            foreignLivedata.value?.let { foreign ->
                catNameLivedata.value?.let { catName ->
                    drillerUseCases.updateIsWordActiveUseCase.invoke(nativ, foreign, catName, false)
                }
            }
        }
    }
}

class EditAddWordViewModelFactory @Inject constructor(
    private val drillerUseCases: DrillerUseCases,
    @ApplicationScope private val applicationScope: CoroutineScope // todo работает норм?
) : ViewModelAssistedFactory<EditAddWordViewModel> {
    override fun create(handle: SavedStateHandle): EditAddWordViewModel {
        return EditAddWordViewModel(drillerUseCases, handle, applicationScope)
    }
}

//class EditAddWordViewModelFactory @AssistedInject constructor(
//    private val drillerUseCases: DrillerUseCases,
//    @Assisted owner: SavedStateRegistryOwner,
//    @ApplicationScope private val applicationScope: CoroutineScope // todo работает норм?
//) : AbstractSavedStateViewModelFactory(owner, null) {
//    override fun <T : ViewModel?> create(
//        key: String,
//        modelClass: Class<T>,
//        handle: SavedStateHandle
//    ): T = EditAddWordViewModel(drillerUseCases, handle, applicationScope) as T
//}
//
//@AssistedFactory
//interface EditAddWordViewModelAssistedFactory {
//    fun create(owner: SavedStateRegistryOwner): EditAddWordViewModelFactory
//}
