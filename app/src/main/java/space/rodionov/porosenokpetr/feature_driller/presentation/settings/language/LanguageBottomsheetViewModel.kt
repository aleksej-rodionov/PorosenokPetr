package space.rodionov.porosenokpetr.feature_driller.presentation.settings.language

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.*
import space.rodionov.porosenokpetr.feature_driller.utils.AppFlavor
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.FOREIGN_LANGUAGE_CHANGE
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.NATIVE_LANGUAGE_CHANGE
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_DB_REFACTOR
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_NATIVE_LANG
import javax.inject.Inject

@HiltViewModel
class LanguageBottomsheetViewModel @Inject constructor(
    private val observeNativeLangUseCase: ObserveNativeLangUseCase,
    private val updateNativeLangUseCase: UpdateNativeLangUseCase,
    private val observeLearnedLangUseCase: ObserveLearnedLangUseCase,
    private val updateLearnedLangUseCase: UpdateLearnedLangUseCase,
    private val observeMode: ObserveModeUseCase,
    private val state: SavedStateHandle
) : ViewModel() {
    var nativeOrForeign = state.getLiveData<Int>("nativeForeign", NATIVE_LANGUAGE_CHANGE)

    //============MODE============
    private val _mode = observeMode.invoke()
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    //=========================NATIVE LANG=======================
    private val _nativeLang = observeNativeLangUseCase.invoke()
    val nativeLang= _nativeLang.stateIn(viewModelScope, SharingStarted.Lazily, Constants.NATIVE_LANGUAGE_RU)
    fun updateNativeLang(lang: Int) = viewModelScope.launch { updateNativeLangUseCase.invoke(lang) }

    //================================LEARNED LANG==================================
    private val _learnedLang = observeLearnedLangUseCase.invoke()
    val learnedLang = _learnedLang.stateIn(viewModelScope, SharingStarted.Lazily, Constants.NATIVE_LANGUAGE_EN)
    fun updateLearnedLang(lang: Int) = viewModelScope.launch { updateLearnedLangUseCase.invoke(lang) }

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
}





