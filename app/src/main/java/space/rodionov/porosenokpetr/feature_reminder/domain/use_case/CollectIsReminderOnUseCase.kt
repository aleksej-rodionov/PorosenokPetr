package space.rodionov.porosenokpetr.feature_reminder.domain.use_case

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.UpdateIsReminderOnUseCase.Companion.IS_REMINDER_ON_KEY

class CollectIsReminderOnUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke(): Flow<Boolean> = keyValueStorage.collectValue(IS_REMINDER_ON_KEY, false)
}