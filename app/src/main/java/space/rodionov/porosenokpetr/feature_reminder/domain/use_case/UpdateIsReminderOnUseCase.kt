package space.rodionov.porosenokpetr.feature_reminder.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage

class UpdateIsReminderOnUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    suspend operator fun invoke(isReminderOn: Boolean) =
        keyValueStorage.updateValue(IS_REMINDER_ON_KEY, isReminderOn)

    companion object {
        const val IS_REMINDER_ON_KEY = "is_reminder_on_key"
    }
}