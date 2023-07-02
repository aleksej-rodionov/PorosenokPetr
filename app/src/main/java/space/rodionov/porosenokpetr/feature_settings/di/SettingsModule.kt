package space.rodionov.porosenokpetr.feature_settings.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveFollowSystemModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SetModeUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.*
import javax.inject.Singleton

@Module
class SettingsModule {

    @Provides
    @SettingsScope
    fun provideSetModeUseCase(preferences: Preferences) = SetModeUseCase(preferences)

    @Provides
    @SettingsScope
    fun provideSetFollowSystemModeUseCase(preferences: Preferences) =
        SetFollowSystemModeUseCase(preferences)

    @Provides
    @SettingsScope
    fun provideObserveModeUseCase(preferences: Preferences) = ObserveModeUseCase(preferences)

    @Provides
    @SettingsScope
    fun provideObserveFollowSystemModeUseCase(preferences: Preferences) = ObserveFollowSystemModeUseCase(preferences)
}