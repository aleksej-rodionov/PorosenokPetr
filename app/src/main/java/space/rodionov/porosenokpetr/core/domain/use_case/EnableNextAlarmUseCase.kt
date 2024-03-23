package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.ReminderRepository


/**
 * Вызывается в 2х местах:
 * 1) При переключении свича напоминания в настройках в положение "вкл"
 * 2) При срабатывании Аларма (получении его в Ресивере), чтобы завести новый Аларм
 */
class EnableNextAlarmUseCase(
    private val getReminderTimeUseCase: GetReminderTimeUseCase,
    private val reminderRepository: ReminderRepository
) {

    operator fun invoke() {
        val reminderTime = getReminderTimeUseCase.invoke()
        reminderRepository.enable(reminderTime)
    }
}