package space.rodionov.porosenokpetr.feature_reminder.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.ReminderRepository

/**
 * Вызывается в случаях:
 * 1) Переключения свича напоминания в настройках в положение "выкл"
 * (а точнее при реакции обсервера на изменение этого свойства в kvs)
 *
 * - Посылает интент АлармМенеджеру с командой .cancel(alarm) ??
 */
class CancelAlarmUseCase(
    private val reminderRepository: ReminderRepository
) {

    operator fun invoke() {
        reminderRepository.disable()
    }
}