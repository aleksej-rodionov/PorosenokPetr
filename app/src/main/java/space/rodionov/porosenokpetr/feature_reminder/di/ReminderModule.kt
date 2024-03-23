package space.rodionov.porosenokpetr.feature_reminder.di

import dagger.Module
import dagger.Provides
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.CollectIsReminderOnUseCase
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.CollectReminderTimeUseCase
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.UpdateIsReminderOnUseCase
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

    @Provides
    @ReminderScope
    fun provideCollectIsReminderOnUseCase(keyValueStorage: KeyValueStorage) =
        CollectIsReminderOnUseCase(keyValueStorage)

    @Provides
    @ReminderScope
    fun provideUpdateIsReminderOnUseCase(keyValueStorage: KeyValueStorage) =
        UpdateIsReminderOnUseCase(keyValueStorage)
}