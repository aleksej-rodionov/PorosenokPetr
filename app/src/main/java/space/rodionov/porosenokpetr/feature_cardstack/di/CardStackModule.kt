package space.rodionov.porosenokpetr.feature_cardstack.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.*
import javax.inject.Singleton

@Module
class CardStackModule {

    @Provides
    @Singleton
    fun provideCardStackUseCases(
        repo: WordRepo,
    ): CardStackUseCases {
        return CardStackUseCases(
            getRandomWordUseCase = GetRandomWordUseCase(repo),
            isCategoryActiveUseCase = IsCategoryActiveUseCase(repo),
            getAllCatsNamesUseCase = GetAllCatsNamesUseCase(repo),
            getAllActiveCatsNamesUseCase = GetAllActiveCatsNamesUseCase(repo),
            updateWordIsActiveUseCase = UpdateWordIsActiveUseCase(repo),
            getTenWordsUseCase = GetTenWordsUseCase(repo)
        )
    }
}