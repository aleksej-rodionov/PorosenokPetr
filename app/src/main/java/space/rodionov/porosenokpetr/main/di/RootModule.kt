package space.rodionov.porosenokpetr.main.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveFollowSystemModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SetModeUseCase

@Module
class RootModule {

    @Provides
    @RootScope
    fun provideObserveModeUseCase(
        preferences: Preferences
    ) = ObserveModeUseCase(preferences)

    @Provides
    @RootScope
    fun provideSetModeUseCase(
        preferences: Preferences
    ) = SetModeUseCase(preferences)

    @Provides
    @RootScope
    fun provideObserveFollowSystemModeUseCase(
        preferences: Preferences
    ) = ObserveFollowSystemModeUseCase(preferences)
}