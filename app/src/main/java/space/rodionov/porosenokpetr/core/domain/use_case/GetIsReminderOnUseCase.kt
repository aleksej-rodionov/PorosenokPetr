package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.SetIsReminderOnUseCase.Companion.IS_REMINDER_ON_KEY

class GetIsReminderOnUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke(): Boolean = keyValueStorage.getValue(IS_REMINDER_ON_KEY, false)
}