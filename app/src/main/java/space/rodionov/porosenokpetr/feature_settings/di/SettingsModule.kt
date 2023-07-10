package space.rodionov.porosenokpetr.feature_settings.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.CollectIsFollowingSystemModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectNativeLanguageUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectTranslationDirectionUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateModeUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateIsFollowingSystemModeUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateNativeLanguageUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateTranslationDirectionUseCase
import space.rodionov.porosenokpetr.feature_settings.presentation.SettingsViewModel

@Module
class SettingsModule {

    @Provides
    @SettingsScope
    fun provideObserveModeUseCase(keyValueStorage: KeyValueStorage) =
        CollectModeUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideObserveFollowSystemModeUseCase(keyValueStorage: KeyValueStorage) =
        CollectIsFollowingSystemModeUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideCollectNativeLanguageUseCase(keyValueStorage: KeyValueStorage) =
        CollectNativeLanguageUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideCollectTranslationDirectionUseCase(keyValueStorage: KeyValueStorage) =
        CollectTranslationDirectionUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideSetModeUseCase(keyValueStorage: KeyValueStorage) = UpdateModeUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideSetFollowSystemModeUseCase(keyValueStorage: KeyValueStorage) =
        UpdateIsFollowingSystemModeUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideUpdateNativeLanguageUseCase(keyValueStorage: KeyValueStorage) =
        UpdateNativeLanguageUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideUpdateTranslationDirectionUseCase(keyValueStorage: KeyValueStorage) =
        UpdateTranslationDirectionUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideSettingsViewModel(
        collectModeUseCase: CollectModeUseCase,
        collectIsFollowingSystemModeUseCase: CollectIsFollowingSystemModeUseCase,
        collectNativeLanguageUseCase: CollectNativeLanguageUseCase,
        collectTranslationDirectionUseCase: CollectTranslationDirectionUseCase,
        updateModeUseCase: UpdateModeUseCase,
        updateIsFollowingSystemModeUseCase: UpdateIsFollowingSystemModeUseCase,
        updateNativeLanguageUseCase: UpdateNativeLanguageUseCase,
        updateTranslationDirectionUseCase: UpdateTranslationDirectionUseCase
    ) = SettingsViewModel(
        collectModeUseCase,
        collectIsFollowingSystemModeUseCase,
        collectNativeLanguageUseCase,
        collectTranslationDirectionUseCase,
        updateModeUseCase,
        updateIsFollowingSystemModeUseCase,
        updateNativeLanguageUseCase,
        updateTranslationDirectionUseCase
    )
}