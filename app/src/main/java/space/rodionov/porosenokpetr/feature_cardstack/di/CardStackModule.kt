package space.rodionov.porosenokpetr.feature_cardstack.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SpeakWordUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateLearnedPercentInCategoryUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateWordStatusUseCase
import space.rodionov.porosenokpetr.core.util.SwedishSpeaker
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.*
import javax.inject.Singleton

@Module
class CardStackModule {

    @Provides
//    @CardStackScope
    @Singleton
    fun provideGetRandomWordUseCase(repo: WordRepo) = GetRandomWordUseCase(repo)

    @Provides
//    @CardStackScope
    @Singleton
    fun provideGetTenWordsUseCase(repo: WordRepo) = GetTenWordsUseCase(repo)

//    @Provides //todo temporarily commented not to be bound multiple times
////    @CardStackScope
//    @Singleton
//    fun provideObserveModeUseCase(preferences: Preferences) = ObserveModeUseCase(preferences)

    @Provides
//    @CardStackScope
    @Singleton
    fun provideUpdateWordStatusUseCase(repo: WordRepo) = UpdateWordStatusUseCase(repo)

    @Provides
//    @CardStackScope
    @Singleton
    fun provideSpeakWordUseCase(swedishSpeaker: SwedishSpeaker) = SpeakWordUseCase(swedishSpeaker)

    @Provides
//    @VocabularyScope
    @Singleton
    fun provideUpdateLearnedPercentInCategoryUseCase(repo: WordRepo) = UpdateLearnedPercentInCategoryUseCase(repo)
}