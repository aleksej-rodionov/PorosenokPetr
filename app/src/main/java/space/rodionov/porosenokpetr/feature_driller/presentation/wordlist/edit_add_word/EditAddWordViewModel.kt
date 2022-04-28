package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist.edit_add_word

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import javax.inject.Inject

@HiltViewModel
class EditAddWordViewModel @Inject constructor(
    private val observeNativeLangUseCase: ObserveNativeLangUseCase,
    private val observeLearnedLangUseCase: ObserveLearnedLangUseCase,
    private val observeWordUseCase: ObserveWordUseCase,
    private val updateIsWordActiveUseCase: UpdateIsWordActiveUseCase,
    private val observeMode: ObserveModeUseCase,
    private val state: SavedStateHandle
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
        observeWordUseCase.invoke(rus, eng, catName)
    }
    val word = _word.stateIn(viewModelScope, SharingStarted.Lazily, null)

    //====================================NATIVE LANG==================================
    private val _nativeLang = observeNativeLangUseCase.invoke()
    val nativeLang = _nativeLang.stateIn(viewModelScope, SharingStarted.Lazily, Constants.LANGUAGE_RU)

    //============MODE============
    private val _mode = observeMode.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, 0)



    //======================UI EVENTS=================================================
    private val _eventFlow = MutableSharedFlow<EditAddWOrdEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class EditAddWOrdEvent{
        object ShowInfo : EditAddWOrdEvent()
    }



    //=======================================METHODS========================================
    fun activateWord() = viewModelScope.launch {
        nativLivedata.value?.let { nativ ->
            foreignLivedata.value?.let { foreign ->
                catNameLivedata.value?.let { catName ->
                    updateIsWordActiveUseCase.invoke(nativ, foreign, catName, true)
                }
            }
        }
    }

    fun inactivateWord() = viewModelScope.launch {
        nativLivedata.value?.let { nativ ->
            foreignLivedata.value?.let { foreign ->
                catNameLivedata.value?.let { catName ->
                    updateIsWordActiveUseCase.invoke(nativ, foreign, catName, false)
                }
            }
        }
    }

}