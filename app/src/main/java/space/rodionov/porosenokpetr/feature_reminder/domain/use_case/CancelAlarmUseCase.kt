package space.rodionov.porosenokpetr.feature_reminder.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.ReminderAlarmManager

/**
 * Вызывается в случаях:
 * 1) Переключения свича напоминания в настройках в положение "выкл"
 * (а точнее при реакции обсервера на изменение этого свойства в kvs)
 *
 * - Посылает интент АлармМенеджеру с командой .cancel(alarm) ??
 */
class CancelAlarmUseCase(
    private val reminderAlarmManager: ReminderAlarmManager
) {

    operator fun invoke() {
        reminderAlarmManager.disable()
    }
}