package space.rodionov.porosenokpetr.feature_settings.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.CollectIsFollowingSystemModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateModeUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateIsFollowingSystemModeUseCase
import space.rodionov.porosenokpetr.feature_settings.presentation.SettingsViewModel

@Module
class SettingsModule {

    @Provides
    @SettingsScope
    fun provideSetModeUseCase(keyValueStorage: KeyValueStorage) = UpdateModeUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideSetFollowSystemModeUseCase(keyValueStorage: KeyValueStorage) =
        UpdateIsFollowingSystemModeUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideObserveModeUseCase(keyValueStorage: KeyValueStorage) = CollectModeUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideObserveFollowSystemModeUseCase(keyValueStorage: KeyValueStorage) =
        CollectIsFollowingSystemModeUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideSettingsViewModel(
        updateModeUseCase: UpdateModeUseCase,
        updateIsFollowingSystemModeUseCase: UpdateIsFollowingSystemModeUseCase,
        collectModeUseCase: CollectModeUseCase,
        collectIsFollowingSystemModeUseCase: CollectIsFollowingSystemModeUseCase
    ) = SettingsViewModel(
        updateModeUseCase,
        updateIsFollowingSystemModeUseCase,
        collectModeUseCase,
        collectIsFollowingSystemModeUseCase
    )
}