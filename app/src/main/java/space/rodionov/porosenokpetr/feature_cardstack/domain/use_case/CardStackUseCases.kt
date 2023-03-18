package space.rodionov.porosenokpetr.feature_cardstack.domain.use_case

data class CardStackUseCases(
    val isCategoryActiveUseCase: IsCategoryActiveUseCase,
    val getRandomWordUseCase: GetRandomWordUseCase,
    val getAllCatsNamesUseCase: GetAllCatsNamesUseCase,
    val getAllActiveCatsNamesUseCase: GetAllActiveCatsNamesUseCase,
    val updateWordIsActiveUseCase: UpdateWordIsActiveUseCase,
    val getTenWordsUseCase: GetTenWordsUseCase
)
