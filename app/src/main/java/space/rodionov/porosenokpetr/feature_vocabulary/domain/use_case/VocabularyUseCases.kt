package space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case

import space.rodionov.porosenokpetr.feature_driller.domain.use_cases.UpdateIsWordActiveUseCase

data class VocabularyUseCases(
    val observeWordUseCase: ObserveWordUseCase,
    val observeWordsSearchQueryUseCase: ObserveWordsSearchQueryUseCase,
    val updateWordUseCase: UpdateWordUseCase,
    val updateIsWordActiveUseCase: UpdateIsWordActiveUseCase,
    val observeAllCatsWithWordsUseCase: ObserveAllCatsWithWordsUseCase,
    val observeAllActiveCatsNamesUseCase: ObserveAllActiveCatsNamesUseCase,
    val observeWordsBySearchQueryInCategories: ObserveWordsBySearchQueryInCategories,
    val getWordsBySearchQueryInCategories: GetWordsBySearchQueryInCategories
)
