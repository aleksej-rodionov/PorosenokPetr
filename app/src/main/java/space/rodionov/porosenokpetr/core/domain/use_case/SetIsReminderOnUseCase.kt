package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage

class SetIsReminderOnUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke(isOn: Boolean) {
        keyValueStorage.setValue(IS_REMINDER_ON_KEY, isOn)
    }

    companion object {
        const val IS_REMINDER_ON_KEY = "is_reminder_on_key"
    }
}