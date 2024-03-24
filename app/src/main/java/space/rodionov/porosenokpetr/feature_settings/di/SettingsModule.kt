package space.rodionov.porosenokpetr.feature_settings.di

import android.app.Application
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.repository.ReminderRepository
import space.rodionov.porosenokpetr.core.domain.use_case.CheckIfAlarmSetUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectAvailableNativeLanguagesUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectIsFollowingSystemModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectModeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectNativeLanguageUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CollectTranslationDirectionUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.EnableNextAlarmUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.GetIsReminderOnUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.GetReminderTimeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.SetIsReminderOnUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateInterfaceLanguageUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateModeUseCase
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.CancelAlarmUseCase
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.SetReminderTimeUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.CollectInterfaceLanguageUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.SetInterfaceLocaleConfigUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateIsFollowingSystemModeUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateNativeLanguageUseCase
import space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case.UpdateTranslationDirectionUseCase
import space.rodionov.porosenokpetr.feature_settings.presentation.SettingsViewModel
import space.rodionov.porosenokpetr.main.di.AppCoroutineScopeQualifier

@Module
class SettingsModule {

    @Provides
    @SettingsScope
    fun provideCollectModeUseCase(keyValueStorage: KeyValueStorage) =
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
    fun provideCollectInterfaceLanguageUseCase(keyValueStorage: KeyValueStorage) =
        CollectInterfaceLanguageUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideCollectTranslationDirectionUseCase(keyValueStorage: KeyValueStorage) =
        CollectTranslationDirectionUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideCollectAvailableNativeLanguagesUseCase(keyValueStorage: KeyValueStorage) =
        CollectAvailableNativeLanguagesUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideUpdateModeUseCase(keyValueStorage: KeyValueStorage) =
        UpdateModeUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideUpdateFollowSystemModeUseCase(keyValueStorage: KeyValueStorage) =
        UpdateIsFollowingSystemModeUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideUpdateNativeLanguageUseCase(keyValueStorage: KeyValueStorage) =
        UpdateNativeLanguageUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideUpdateInterfaceLanguageUseCase(keyValueStorage: KeyValueStorage) =
        UpdateInterfaceLanguageUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideSetInterfaceLocaleConfigUseCase(
        app: Application,
        @AppCoroutineScopeQualifier appCoroutineScope: CoroutineScope,
        updateInterfaceLanguageUseCase: UpdateInterfaceLanguageUseCase
    ) = SetInterfaceLocaleConfigUseCase(app, appCoroutineScope, updateInterfaceLanguageUseCase)

    @Provides
    @SettingsScope
    fun provideUpdateTranslationDirectionUseCase(keyValueStorage: KeyValueStorage) =
        UpdateTranslationDirectionUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideGetReminderTimeUseCase(keyValueStorage: KeyValueStorage) =
        GetReminderTimeUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideSetAlarmUseCase(
        getReminderTimeUseCase: GetReminderTimeUseCase,
        reminderRepository: ReminderRepository,
    ) = EnableNextAlarmUseCase(
        getReminderTimeUseCase,
        reminderRepository,
    )

    @Provides
    @SettingsScope
    fun provideCheckIfAlarmSetUseCasee(reminderRepository: ReminderRepository) =
        CheckIfAlarmSetUseCase(reminderRepository)

    @Provides
    @SettingsScope
    fun provideCancelAlarmUseCase(reminderRepository: ReminderRepository) =
        CancelAlarmUseCase(reminderRepository)

    @Provides
    @SettingsScope
    fun provideSetIsReminderOnUseCase(keyValueStorage: KeyValueStorage) =
        SetIsReminderOnUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideGetIsReminderOnUseCase(keyValueStorage: KeyValueStorage) =
        GetIsReminderOnUseCase(keyValueStorage)

    @Provides
    @SettingsScope
    fun provideSetReminderTimeUseCase(keyValueStorage: KeyValueStorage) =
        SetReminderTimeUseCase(keyValueStorage)


    @Provides
    @SettingsScope
    fun provideSettingsViewModel(
        collectModeUseCase: CollectModeUseCase,
        collectIsFollowingSystemModeUseCase: CollectIsFollowingSystemModeUseCase,
        collectNativeLanguageUseCase: CollectNativeLanguageUseCase,
        collectInterfaceLanguageUseCase: CollectInterfaceLanguageUseCase,
        collectTranslationDirectionUseCase: CollectTranslationDirectionUseCase,
        collectAvailableNativeLanguagesUseCase: CollectAvailableNativeLanguagesUseCase,
        updateModeUseCase: UpdateModeUseCase,
        updateIsFollowingSystemModeUseCase: UpdateIsFollowingSystemModeUseCase,
        updateNativeLanguageUseCase: UpdateNativeLanguageUseCase,
        setInterfaceLocaleConfigUseCase: SetInterfaceLocaleConfigUseCase,
        updateTranslationDirectionUseCase: UpdateTranslationDirectionUseCase,
        checkIfAlarmSetUseCase: CheckIfAlarmSetUseCase,
        enableNextAlarmUseCase: EnableNextAlarmUseCase,
        cancelAlarmUseCase: CancelAlarmUseCase,
        setIsReminderOnUseCase: SetIsReminderOnUseCase,
        getIsReminderOnUseCase: GetIsReminderOnUseCase,
        setReminderTimeUseCase: SetReminderTimeUseCase,
        getReminderTimeUseCase: GetReminderTimeUseCase,
    ) = SettingsViewModel(
        collectModeUseCase,
        collectIsFollowingSystemModeUseCase,
        collectNativeLanguageUseCase,
        collectInterfaceLanguageUseCase,
        collectTranslationDirectionUseCase,
        collectAvailableNativeLanguagesUseCase,
        updateModeUseCase,
        updateIsFollowingSystemModeUseCase,
        updateNativeLanguageUseCase,
        setInterfaceLocaleConfigUseCase,
        updateTranslationDirectionUseCase,
        checkIfAlarmSetUseCase,
        enableNextAlarmUseCase,
        cancelAlarmUseCase,
        setIsReminderOnUseCase,
        getIsReminderOnUseCase,
        setReminderTimeUseCase,
        getReminderTimeUseCase,
    )
}