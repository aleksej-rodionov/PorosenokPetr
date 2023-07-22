package space.rodionov.porosenokpetr.feature_launcher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.UiText
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_launcher.domain.use_case.CheckInterfaceLocaleConfigUseCase
import space.rodionov.porosenokpetr.feature_launcher.domain.use_case.CheckVocabularyUseCase
import space.rodionov.porosenokpetr.main.presentation.RootActivity.Companion.ROOT_ACTIVITY
import javax.inject.Inject

class LauncherViewModel @Inject constructor(
    private val checkVocabularyUseCase: CheckVocabularyUseCase,
    private val checkInterfaceLocaleConfigUseCase: CheckInterfaceLocaleConfigUseCase
) : ViewModel() {

    private val _uiEffect = Channel<UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (checkVocabularyUseCase.invoke()) {
                _uiEffect.send(UiEffect.NavigateTo(ROOT_ACTIVITY))
            } else {
                _uiEffect.send(UiEffect.ShowSnackbar(UiText.StringResource(R.string.no_vocabulary)))
            }
        }

        checkInterfaceLocaleConfigUseCase.invoke()
    }
}