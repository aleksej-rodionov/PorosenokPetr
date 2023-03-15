package space.rodionov.porosenokpetr.feature_driller.presentation.settings.language

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.core.util.AppFlavor
import space.rodionov.porosenokpetr.core.util.Constants
import space.rodionov.porosenokpetr.core.util.Constants.FOREIGN_LANGUAGE_CHANGE
import space.rodionov.porosenokpetr.core.util.Constants.NATIVE_LANGUAGE_CHANGE
import space.rodionov.porosenokpetr.core.util.ViewModelAssistedFactory
import space.rodionov.porosenokpetr.core.domain.use_case.SharedUseCases
import javax.inject.Inject

class LanguageBottomsheetViewModel (
    private val sharedUseCases: SharedUseCases,
    private val preferenvesUseCases: PreferencesUseCases,
    private val state: SavedStateHandle,
) : ViewModel() {
    var nativeOrForeign = state.getLiveData<Int>("nativeForeign", NATIVE_LANGUAGE_CHANGE)

    //============MODE============
    private val _mode = preferenvesUseCases.observeModeUseCase.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    //=========================NATIVE LANG=======================
    private val _nativeLang = sharedUseCases.observeNativeLangUseCase.invoke()
    val nativeLang= _nativeLang.stateIn(viewModelScope, SharingStarted.Lazily, Constants.LANGUAGE_RU)
    fun updateNativeLang(lang: Int) = viewModelScope.launch { sharedUseCases.updateNativeLangUseCase.invoke(lang) }

    //================================LEARNED LANG==================================
    private val _learnedLang = sharedUseCases.observeLearnedLangUseCase.invoke()
    val learnedLang = _learnedLang.stateIn(viewModelScope, SharingStarted.Lazily, Constants.LANGUAGE_EN)
    fun updateLearnedLang(lang: Int) = viewModelScope.launch { sharedUseCases.updateLearnedLangUseCase.invoke(lang) }

    //===============================LIST================================
    val initialList = mutableListOf<LanguageItem>()

    private val _langList = MutableStateFlow<List<LanguageItem>>(initialList)
    val langList = _langList.asStateFlow()

    fun inflateLanguageList(app: AppFlavor) {
        val langs =  mutableListOf<LanguageItem>()
        when (nativeOrForeign.value) {
            NATIVE_LANGUAGE_CHANGE -> langs.addAll(LanguageHelper.getNativeLanguages(app))
            FOREIGN_LANGUAGE_CHANGE -> langs.addAll(LanguageHelper.getLearnedLanguages(app))
            else -> {
//                Log.d(TAG_DB_REFACTOR, "inflateLanguageList: else branch")
//                LanguageHelper.getNativeLanguages(app)
//                mutableListOf<LanguageItem>()
            }
        }
        _langList.value = langs.toMutableList()
    }

    //===========================METHODS==============================
    fun onLangChecked(lang: Int) = viewModelScope.launch {
        if (nativeOrForeign.value == NATIVE_LANGUAGE_CHANGE) {
            updateNativeLang(lang)
        } else {
            updateLearnedLang(lang)
        }
    }

    fun refreshList() = viewModelScope.launch {
        val langs = langList.value.toMutableList()
        _langList.value = langs
    }
}

class LanguageBottomsheetViewModelFactory @Inject constructor(
    private val sharedUseCases: SharedUseCases,
    private val preferenvesUseCases: PreferencesUseCases
) : ViewModelAssistedFactory<LanguageBottomsheetViewModel> {
    override fun create(handle: SavedStateHandle): LanguageBottomsheetViewModel {
        return LanguageBottomsheetViewModel(sharedUseCases, preferenvesUseCases, handle)
    }
}

//class LanguageBottomsheetViewModelFactory @AssistedInject constructor(
//    private val drillerUseCases: DrillerUseCases,
//    @Assisted owner: SavedStateRegistryOwner,
//) : AbstractSavedStateViewModelFactory(owner, null) {
//    override fun <T : ViewModel?> create(
//        key: String,
//        modelClass: Class<T>,
//        handle: SavedStateHandle
//    ): T = LanguageBottomsheetViewModel(drillerUseCases, handle) as T
//}
//
//@AssistedFactory
//interface LanguageBottomsheetViewModelAssistedFactory {
//    fun create(owner: SavedStateRegistryOwner): LanguageBottomsheetViewModelFactory
//}





