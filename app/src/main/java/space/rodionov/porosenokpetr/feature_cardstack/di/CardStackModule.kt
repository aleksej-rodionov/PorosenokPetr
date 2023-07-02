package space.rodionov.porosenokpetr.feature_cardstack.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SpeakWordUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateWordStatusUseCase
import space.rodionov.porosenokpetr.core.util.SwedishSpeaker
import space.rodionov.porosenokpetr.feature_cardstack.domain.use_case.*
import javax.inject.Singleton

@Module
class CardStackModule {

    @Provides
    @CardStackScope
    fun provideGetRandomWordUseCase(repo: WordRepo) = GetRandomWordUseCase(repo)

    @Provides
    @CardStackScope
    fun provideGetTenWordsUseCase(repo: WordRepo) = GetTenWordsUseCase(repo)

    @Provides
    @CardStackScope
    fun provideObserveModeUseCase(preferences: Preferences) = ObserveModeUseCase(preferences)

    @Provides
    @CardStackScope
    fun provideUpdateWordStatusUseCase(repo: WordRepo) = UpdateWordStatusUseCase(repo)

    @Provides
    @CardStackScope
    fun provideSpeakWordUseCase(swedishSpeaker: SwedishSpeaker) = SpeakWordUseCase(swedishSpeaker)
}