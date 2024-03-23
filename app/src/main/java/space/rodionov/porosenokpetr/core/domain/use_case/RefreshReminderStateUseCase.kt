package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.SetIsReminderOnUseCase.Companion.IS_REMINDER_ON_KEY
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.CancelAlarmUseCase

/**
 * //todo в каких местах этот проверка дергать?
 * - при каждом запуске
 * - после срабатывания очередного Аларма
 */
class RefreshReminderStateUseCase(
    private val keyValueStorage: KeyValueStorage,
    private val enableNextAlarmUseCase: EnableNextAlarmUseCase,
    private val cancelAlarmUseCase: CancelAlarmUseCase,
) {

    operator fun invoke() {
        val isReminderOn = keyValueStorage.getValue(IS_REMINDER_ON_KEY, false)
        if (isReminderOn) {
            enableNextAlarmUseCase.invoke()
        } else {
            cancelAlarmUseCase.invoke()
        }
    }
}