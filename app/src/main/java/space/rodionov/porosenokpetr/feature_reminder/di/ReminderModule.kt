package space.rodionov.porosenokpetr.feature_reminder.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.repository.ReminderAlarmManager
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.CancelAlarmUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.CheckIfAlarmSetUseCase
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.CollectReminderTimeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.GetReminderTimeUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.EnableNextAlarmUseCase
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.UpdateReminderTimeUseCase

@Module
class ReminderModule {

    @Provides
    @ReminderScope
    fun provideGetReminderTimeUseCase(keyValueStorage: KeyValueStorage) =
        GetReminderTimeUseCase(keyValueStorage)

    @Provides
    @ReminderScope
    fun provideCollectReminderTimeUseCase(keyValueStorage: KeyValueStorage) =
        CollectReminderTimeUseCase(keyValueStorage)

    @Provides
    @ReminderScope
    fun provideUpdateReminderTimeUseCase(keyValueStorage: KeyValueStorage) =
        UpdateReminderTimeUseCase(keyValueStorage)

    @Provides
    @ReminderScope
    fun provideSetAlarmUseCase(
        getReminderTimeUseCase: GetReminderTimeUseCase,
        reminderAlarmManager: ReminderAlarmManager
    ) = EnableNextAlarmUseCase(
        getReminderTimeUseCase,
        reminderAlarmManager
    )

    @Provides
    @ReminderScope
    fun provideCheckIfAlarmSetUseCasee(reminderAlarmManager: ReminderAlarmManager) = CheckIfAlarmSetUseCase(reminderAlarmManager)

    @Provides
    @ReminderScope
    fun provideCancelAlarmUseCase(reminderAlarmManager: ReminderAlarmManager) = CancelAlarmUseCase(reminderAlarmManager)
}