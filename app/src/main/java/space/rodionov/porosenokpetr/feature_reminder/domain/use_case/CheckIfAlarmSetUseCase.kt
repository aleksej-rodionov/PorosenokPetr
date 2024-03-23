package space.rodionov.porosenokpetr.feature_reminder.domain.use_case

import android.content.Context

/**
 * Проверяет есть заведен ли наш аларм в АлармМенеджере
 *
 * - проверяется при открытии экрана настроек для отображения
 * - также при изменении в isReminderOn св-ва в kvs в обсервере прям в SettingsVM [vmeste s isReminderOn v kvs]
 * - также при каждом запуске в Launcher-е [vmeste s isReminderOn v kvs]
 */
class CheckIfAlarmSetUseCase(
    private val context: Context
) {

    operator fun invoke(): Boolean {
        //todo
        return false
    }
}