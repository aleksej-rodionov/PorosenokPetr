package space.rodionov.porosenokpetr.core.domain.use_case

import android.util.Log
import space.rodionov.porosenokpetr.core.domain.common.LocalTimeFormatter
import space.rodionov.porosenokpetr.core.domain.repository.ReminderAlarmManager

private const val TAG = "EnableNextAlarmUseCaseTAGGY"

/**
 * Вызывается в 2х местах:
 * 1) При переключении свича напоминания в настройках в положение "вкл"
 * 2) При срабатывании Аларма (получении его в Ресивере), чтобы завести новый Аларм
 */
class EnableNextAlarmUseCase(
    private val getReminderTimeUseCase: GetReminderTimeUseCase,
    private val reminderAlarmManager: ReminderAlarmManager
) {

    operator fun invoke() {
        val reminderTime = getReminderTimeUseCase.invoke()
        Log.d(TAG, "invoke: enableUseCase kvs.getReminderTime = ${LocalTimeFormatter.serializeToString(reminderTime)}")
        reminderAlarmManager.enable(reminderTime)
    }
}