package space.rodionov.porosenokpetr.feature_settings.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveFollowSystemModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.ObserveModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SetModeUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.SetFollowSystemModeUseCase
import space.rodionov.porosenokpetr.feature_settings.presentation.SettingsViewModel

@Module
class SettingsModule {

    @Provides
    @SettingsScope
    fun provideSetModeUseCase(keyValueStorage: KeyValueStorage) = SetModeUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideSetFollowSystemModeUseCase(keyValueStorage: KeyValueStorage) =
        SetFollowSystemModeUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideObserveModeUseCase(keyValueStorage: KeyValueStorage) = ObserveModeUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideObserveFollowSystemModeUseCase(keyValueStorage: KeyValueStorage) =
        ObserveFollowSystemModeUseCase(keyValueStorage)

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