package space.rodionov.porosenokpetr.feature_reminder.domain.use_case

import android.content.Context

/**
 * Вызывается в 2х местах:
 * 1) При переключении свича напоминания в настройках в положение "вкл"
 * 2) При срабатывании Аларма (получении его в Ресивере) после проверки, что напоминание все еще
 * сохранено как "вкл" в БД (+ там показ  собственно пуша тоже после той проверки)
 */
class SetAlarmUseCase(
    private val context: Context
) {

    operator fun invoke() {
        //todo
    }
}