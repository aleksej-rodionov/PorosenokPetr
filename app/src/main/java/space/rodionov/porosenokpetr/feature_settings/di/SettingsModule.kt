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

//    @Provides //todo temporarily commented not to be bound multiple times
////    @SettingsScope
//    @Singleton
//    fun provideSetModeUseCase(preferences: Preferences) = SetModeUseCase(preferences)

    @Provides
//    @SettingsScope
    @Singleton
    fun provideSetFollowSystemModeUseCase(preferences: Preferences) =
        SetFollowSystemModeUseCase(preferences)

//    @Provides //todo temporarily commented not to be bound multiple times
////    @SettingsScope
//    @Singleton
//    fun provideObserveModeUseCase(preferences: Preferences) = ObserveModeUseCase(preferences)

//    @Provides //todo temporarily commented not to be bound multiple times
////    @SettingsScope
//    @Singleton
//    fun provideObserveFollowSystemModeUseCase(preferences: Preferences) = ObserveFollowSystemModeUseCase(preferences)
}