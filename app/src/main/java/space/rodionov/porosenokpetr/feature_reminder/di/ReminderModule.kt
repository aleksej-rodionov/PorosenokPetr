package space.rodionov.porosenokpetr.feature_reminder.di

import android.content.Context
import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.repository.ReminderRepository
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
        reminderRepository: ReminderRepository
    ) = EnableNextAlarmUseCase(
        getReminderTimeUseCase,
        reminderRepository
    )

    @Provides
    @ReminderScope
    fun provideCheckIfAlarmSetUseCasee(reminderRepository: ReminderRepository) = CheckIfAlarmSetUseCase(reminderRepository)

    @Provides
    @ReminderScope
    fun provideCancelAlarmUseCase(reminderRepository: ReminderRepository) = CancelAlarmUseCase(reminderRepository)
}