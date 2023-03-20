package space.rodionov.porosenokpetr.feature_vocabulary.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case.*
import javax.inject.Singleton

@Module
class VocabularyModule {

    @Provides
    @Singleton
    fun provideVocabularyUseCases(
        repo: WordRepo,
    ): VocabularyUseCases {
        return VocabularyUseCases(
            observeWordUseCase = ObserveWordUseCase(repo),
            observeWordsSearchQueryUseCase = ObserveWordsSearchQueryUseCase(repo),
            observeAllCatsWithWordsUseCase = ObserveAllCatsWithWordsUseCase(repo),
            observeAllActiveCatsNamesUseCase = ObserveAllActiveCatsNamesUseCase(repo),
            observeWordsBySearchQueryInCategories = ObserveWordsBySearchQueryInCategories(repo),
            getWordsBySearchQueryInCategories = GetWordsBySearchQueryInCategories(repo)
        )
    }
}