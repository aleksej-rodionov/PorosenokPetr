package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.ReminderRepository

/**
 * Проверяет есть заведен ли наш аларм в АлармМенеджере
 *
 * - проверяется при открытии экрана настроек для отображения
 * - также при изменении в isReminderOn св-ва в kvs в обсервере прям в SettingsVM [vmeste s isReminderOn v kvs]
 * - также при каждом запуске в Launcher-е [vmeste s isReminderOn v kvs]
 */
class CheckIfAlarmSetUseCase(
    private val reminderRepository: ReminderRepository
) {

    operator fun invoke(): Boolean {
        return reminderRepository.check()
    }
}