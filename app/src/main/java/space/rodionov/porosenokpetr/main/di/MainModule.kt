package space.rodionov.porosenokpetr.main.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveFollowSystemModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SetModeUseCase
import javax.inject.Singleton

@Module
class MainModule {

    @Provides
//    @MainScope
    @Singleton
    fun provideObserveModeUseCase(preferences: Preferences) = ObserveModeUseCase(preferences)

    @Provides
//    @MainScope
    @Singleton
    fun provideSetModeUseCase(preferences: Preferences) = SetModeUseCase(preferences)

    @Provides
//    @MainScope
    @Singleton
    fun provideObserveFollowSystemModeUseCase(preferences: Preferences) =
        ObserveFollowSystemModeUseCase(preferences)
}
