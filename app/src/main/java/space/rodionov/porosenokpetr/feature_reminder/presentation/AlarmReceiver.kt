package space.rodionov.porosenokpetr.feature_reminder.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import space.rodionov.porosenokpetr.core.data.repository.ReminderAlarmManagerImpl.Companion.REMINDER_ALARM_ACTION
import space.rodionov.porosenokpetr.feature_reminder.di.DaggerReminderComponent
import space.rodionov.porosenokpetr.main.PorosenokPetrApp

private const val TAG = "AlarmReceiverTAGGY"

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val component = DaggerReminderComponent
            .builder()
            .appComponent(PorosenokPetrApp.component!!)
            .build()
        component.inject(this)

        when (intent?.action) {
            REMINDER_ALARM_ACTION -> {

                Log.d(TAG, "onReceive: Alarm action received")
                val reminderIsOn = true //todo
                val todayStudingSecondsLeft = 84 //todo
                if (reminderIsOn && todayStudingSecondsLeft != 0) {

                    //todo set next Alarm
                    //todo show notification
                }
            }
            else -> Unit
        }
    }
}