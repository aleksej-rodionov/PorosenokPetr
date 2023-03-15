package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist.edit_add_word

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.di.ApplicationScope
import space.rodionov.porosenokpetr.core.util.ViewModelAssistedFactory
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.use_case.SharedUseCases
import space.rodionov.porosenokpetr.core.util.Constants
import javax.inject.Inject

class EditAddWordViewModel (
    private val sharedUseCases: SharedUseCases,
    private val preferenvesUseCases: PreferencesUseCases,
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
        sharedUseCases.observeWordUseCase.invoke(rus, eng, catName)
    }
    val word = _word.stateIn(viewModelScope, SharingStarted.Lazily, null)

    //====================================NATIVE LANG==================================
    private val _nativeLang = sharedUseCases.observeNativeLangUseCase.invoke()
    val nativeLang = _nativeLang.stateIn(viewModelScope, SharingStarted.Lazily, Constants.LANGUAGE_RU)

    //============MODE============
    private val _mode = preferenvesUseCases.observeModeUseCase.invoke()
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
            sharedUseCases.updateWordUseCase.invoke(it, newWord)
        }
    }

    fun activateWord() = viewModelScope.launch {
        nativLivedata.value?.let { nativ ->
            foreignLivedata.value?.let { foreign ->
                catNameLivedata.value?.let { catName ->
                    sharedUseCases.updateIsWordActiveUseCase.invoke(nativ, foreign, catName, true)
                }
            }
        }
    }

    fun inactivateWord() = viewModelScope.launch {
        nativLivedata.value?.let { nativ ->
            foreignLivedata.value?.let { foreign ->
                catNameLivedata.value?.let { catName ->
                    sharedUseCases.updateIsWordActiveUseCase.invoke(nativ, foreign, catName, false)
                }
            }
        }
    }
}

class EditAddWordViewModelFactory @Inject constructor(
    private val sharedUseCases: SharedUseCases,
    private val preferenvesUseCases: PreferencesUseCases,
    @ApplicationScope private val applicationScope: CoroutineScope // todo работает норм?
) : ViewModelAssistedFactory<EditAddWordViewModel> {
    override fun create(handle: SavedStateHandle): EditAddWordViewModel {
        return EditAddWordViewModel(sharedUseCases, preferenvesUseCases, handle, applicationScope)
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
