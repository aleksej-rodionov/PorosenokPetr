package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.GetAllCategoriesUseCase
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.ObserveAllCategoriesUseCase

data class SharedUseCases(
    val observeLearnedLangUseCase: ObserveLearnedLangUseCase,
    val observeNativeLangUseCase: ObserveNativeLangUseCase,
    val observeTranslationDirectionUseCase: ObserveTranslationDirectionUseCase,
    val observeModeUseCase: ObserveModeUseCase,
    val observeFollowSystemModeUseCase: ObserveFollowSystemModeUseCase,
    val setModeUseCase: SetModeUseCase,
    val observeAllCategoriesUseCase: ObserveAllCategoriesUseCase,
    val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    val updateWordIsActiveUseCase: UpdateWordIsActiveUseCase,
    val updateLearnedPercentInCategory: UpdateLearnedPercentInCategory,
    val makeCategoryActiveUseCase: MakeCategoryActiveUseCase
)