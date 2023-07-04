package space.rodionov.porosenokpetr.feature_settings.di

import android.app.Application
import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.data.preferences.PreferencesImpl
import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveFollowSystemModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SetModeUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.*
import space.rodionov.porosenokpetr.feature_settings.presentation.SettingsViewModel
import space.rodionov.porosenokpetr.main.di.TemporaryPrefQualifier
import javax.inject.Singleton

@Module
class SettingsModule {

    @Provides //todo temporarily commented not to be bound multiple times
    @SettingsScope
//    @Singleton
    fun provideSetModeUseCase(/*@TemporarySettingsQualifier*/ preferences: Preferences) = SetModeUseCase(preferences)

    @Provides
    @SettingsScope
//    @Singleton
    fun provideSetFollowSystemModeUseCase(/*@TemporarySettingsQualifier*/ preferences: Preferences) =
        SetFollowSystemModeUseCase(preferences)

    @Provides //todo temporarily commented not to be bound multiple times
    @SettingsScope
//    @Singleton
    fun provideObserveModeUseCase(/*@TemporarySettingsQualifier*/ preferences: Preferences) = ObserveModeUseCase(preferences)

    @Provides //todo temporarily commented not to be bound multiple times
    @SettingsScope
//    @Singleton
    fun provideObserveFollowSystemModeUseCase(/*@TemporarySettingsQualifier*/ preferences: Preferences) = ObserveFollowSystemModeUseCase(preferences)

//    @Provides
//    @SettingsScope
//    @TemporarySettingsQualifier
//    fun provideDatastore(app: Application): Preferences {
//        return PreferencesImpl(app)
//    }

    @Provides
    @SettingsScope
    fun provideSettingsViewModel(
        setModeUseCase: SetModeUseCase,
        setFollowSystemModeUseCase: SetFollowSystemModeUseCase,
        observeModeUseCase: ObserveModeUseCase,
        observeFollowSystemModeUseCase: ObserveFollowSystemModeUseCase
    ) = SettingsViewModel(
        setModeUseCase,
        setFollowSystemModeUseCase,
        observeModeUseCase,
        observeFollowSystemModeUseCase
    )
}