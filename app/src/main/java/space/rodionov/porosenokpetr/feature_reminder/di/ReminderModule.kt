package space.rodionov.porosenokpetr.feature_reminder.di

import android.content.Context
import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.CancelAlarmUseCase
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.CheckIfAlarmSetUseCase
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.CollectReminderTimeUseCase
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.SetAlarmUseCase
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.UpdateReminderTimeUseCase

@Module
class ReminderModule {

    @Provides
    @ReminderScope
    fun provideCollectReminderTimeUseCase(keyValueStorage: KeyValueStorage) =
        CollectReminderTimeUseCase(keyValueStorage)

    @Provides
    @ReminderScope
    fun provideUpdateReminderTimeUseCase(keyValueStorage: KeyValueStorage) =
        UpdateReminderTimeUseCase(keyValueStorage)

//    @Provides
//    @ReminderScope
//    fun provideCollectIsReminderOnUseCase(keyValueStorage: KeyValueStorage) =
//        CollectIsReminderOnUseCase(keyValueStorage)
//
//    @Provides
//    @ReminderScope
//    fun provideUpdateIsReminderOnUseCase(keyValueStorage: KeyValueStorage) =
//        UpdateIsReminderOnUseCase(keyValueStorage)//todo

    @Provides
    @ReminderScope
    fun provideSetAlarmUseCase(context: Context) = SetAlarmUseCase(context)

    @Provides
    @ReminderScope
    fun provideCheckIfAlarmSetUseCasee(context: Context) = CheckIfAlarmSetUseCase(context)

    @Provides
    @ReminderScope
    fun provideCancelAlarmUseCase(context: Context) = CancelAlarmUseCase(context)
}