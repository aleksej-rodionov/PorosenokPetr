package space.rodionov.porosenokpetr.feature_reminder.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import space.rodionov.porosenokpetr.core.data.repository.ReminderAlarmManagerImpl.Companion.REMINDER_ALARM_ACTION
import space.rodionov.porosenokpetr.core.domain.use_case.EnableNextAlarmUseCase
import space.rodionov.porosenokpetr.core.domain.use_case.GetIsReminderOnUseCase
import space.rodionov.porosenokpetr.feature_reminder.di.DaggerReminderComponent
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.CancelAlarmUseCase
import space.rodionov.porosenokpetr.main.PorosenokPetrApp
import javax.inject.Inject

private const val TAG = "AlarmReceiverTAGGY"

class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getIsReminderOnUseCase: GetIsReminderOnUseCase

    @Inject
    lateinit var enableNextAlarmUseCase: EnableNextAlarmUseCase

    @Inject
    lateinit var cancelAlarmUseCase: CancelAlarmUseCase

    override fun onReceive(context: Context?, intent: Intent?) {
        val component = DaggerReminderComponent
            .builder()
            .appComponent(PorosenokPetrApp.component!!)
            .build()
        component.inject(this)

        val reminderNotificator = ReminderNotificator(context!!)

        when (intent?.action) {
            REMINDER_ALARM_ACTION -> {
                Log.d(TAG, "onReceive: REMINDER_ALARM_ACTION")
                val reminderIsOn = getIsReminderOnUseCase.invoke()
                val todayStudingSecondsLeft = 84 //todo
                if (reminderIsOn && todayStudingSecondsLeft != 0) {
                    enableNextAlarmUseCase.invoke()
                    reminderNotificator.showNotification()
                } else {
                    cancelAlarmUseCase.invoke()
                }
            }
            else -> Unit
        }
    }
}