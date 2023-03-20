package space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case

data class VocabularyUseCases(
    val observeWordUseCase: ObserveWordUseCase,
    val observeWordsSearchQueryUseCase: ObserveWordsSearchQueryUseCase,
    val observeAllCatsWithWordsUseCase: ObserveAllCatsWithWordsUseCase,
    val observeAllActiveCatsNamesUseCase: ObserveAllActiveCatsNamesUseCase,
    val observeWordsBySearchQueryInCategories: ObserveWordsBySearchQueryInCategories,
    val getWordsBySearchQueryInCategories: GetWordsBySearchQueryInCategories
)
