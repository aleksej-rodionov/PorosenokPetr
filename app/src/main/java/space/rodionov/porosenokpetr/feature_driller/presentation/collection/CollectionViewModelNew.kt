package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.MakeCategoryActiveUseCase
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.ObserveAllActiveCatsNamesUseCase
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.ObserveAllCatsWithWordsUseCase
import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.ObserveModeUseCase
import javax.inject.Inject

@HiltViewModel
class CollectionViewModelNew @Inject constructor(
    private val observeAllCatsWithWordsUseCase: ObserveAllCatsWithWordsUseCase,
    private val makeCategoryActiveUseCase: MakeCategoryActiveUseCase,
    private val observeAllActiveCatsNamesUseCase: ObserveAllActiveCatsNamesUseCase,
    private val observeMode: ObserveModeUseCase,
    private val state: SavedStateHandle
) : ViewModel() {


}









