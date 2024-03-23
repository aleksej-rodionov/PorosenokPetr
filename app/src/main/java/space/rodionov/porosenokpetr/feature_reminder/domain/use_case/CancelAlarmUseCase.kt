package space.rodionov.porosenokpetr.feature_reminder.domain.use_case

import android.content.Context

/**
 * Вызывается в случаях:
 * 1) Переключения свича напоминания в настройках в положение "выкл"
 * (а точнее при реакции обсервера на изменение этого свойства в kvs)
 * //todo где поместить этот обсервер?
 *
 * - Посылает интент АлармМенеджеру с командой .cancel(alarm) ??
 */
class CancelAlarmUseCase(
    private val context: Context
) {

    operator fun invoke() {
        //todo
    }
}