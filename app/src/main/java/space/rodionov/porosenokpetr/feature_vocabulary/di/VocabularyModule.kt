package space.rodionov.porosenokpetr.feature_vocabulary.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.domain.use_case.MakeCategoryActiveUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SpeakWordUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateLearnedPercentInCategoryUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateWordStatusUseCase
import space.rodionov.porosenokpetr.core.util.SwedishSpeaker
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.ObserveAllCategoriesUseCase
import space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case.*
import javax.inject.Singleton

@Module
class VocabularyModule {

    @Provides
//    @VocabularyScope
    @Singleton
    fun provideObserveAllCategoriesUseCase(repo: WordRepo) = ObserveAllCategoriesUseCase(repo)

    @Provides
//    @VocabularyScope
    @Singleton
    fun provideObserveWordsBySearchQueryInCategories(repo: WordRepo) =
        ObserveWordsBySearchQueryInCategories(repo)

    @Provides
//    @VocabularyScope
    @Singleton
    fun provideMakeCategoryActiveUseCase(repo: WordRepo) = MakeCategoryActiveUseCase(repo)

//    @Provides //todo temporarily commented not to be bound multiple times
////    @VocabularyScope
//    @Singleton
//    fun provideSpeakWordUseCase(swedishSpeaker: SwedishSpeaker) = SpeakWordUseCase(swedishSpeaker)

//    @Provides //todo temporarily commented not to be bound multiple times
////    @VocabularyScope
//    @Singleton
//    fun provideUpdateWordStatusUseCase(repo: WordRepo) = UpdateWordStatusUseCase(repo)

//    @Provides //todo temporarily commented not to be bound multiple times
////    @VocabularyScope
//    @Singleton
//    fun provideUpdateLearnedPercentInCategoryUseCase(repo: WordRepo) = UpdateLearnedPercentInCategoryUseCase(repo)
}