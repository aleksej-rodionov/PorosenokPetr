package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.core.domain.use_case.*

data class DrillerUseCases(
    val observeLearnedLangUseCase: ObserveLearnedLangUseCase,
    val updateLearnedLangUseCase: UpdateLearnedLangUseCase,
    val observeNativeLangUseCase: ObserveNativeLangUseCase,
    val updateNativeLangUseCase: UpdateNativeLangUseCase,
    val saveTranslationDirectionUseCase: SaveTranslationDirectionUseCase,
    val observeTranslationDirectionUseCase: ObserveTranslationDirectionUseCase,
    val updateWordUseCase: UpdateWordUseCase,
    val updateIsWordActiveUseCase: UpdateIsWordActiveUseCase,
    val observeWordUseCase: ObserveWordUseCase,
    val observeWordsSearchQueryUseCase: ObserveWordsSearchQueryUseCase,
    val observeAllCatsWithWordsUseCase: ObserveAllCatsWithWordsUseCase,
    val getCatCompletionPercentUseCase: GetCatCompletionPercentUseCase,
    val observeAllActiveCatsNamesUseCase: ObserveAllActiveCatsNamesUseCase,
    val getRandomWordUseCase: GetRandomWordUseCase,
    val isCategoryActiveUseCase: IsCategoryActiveUseCase,
    val getAllCatsNamesUseCase: GetAllCatsNamesUseCase,
    val getAllActiveCatsNamesUseCase: GetAllActiveCatsNamesUseCase,
    val makeCategoryActiveUseCase: MakeCategoryActiveUseCase,
    val observeAllCategoriesUseCase: ObserveAllCategoriesUseCase,
    val updateWordIsActiveUseCase: UpdateWordIsActiveUseCase,
    val getTenWordsUseCase: GetTenWordsUseCase
) {
}