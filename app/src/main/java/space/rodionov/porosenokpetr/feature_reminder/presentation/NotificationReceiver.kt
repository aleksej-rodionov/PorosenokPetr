package space.rodionov.porosenokpetr.feature_reminder.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import space.rodionov.porosenokpetr.feature_reminder.di.DaggerReminderComponent
import space.rodionov.porosenokpetr.main.PorosenokPetrApp

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val component = DaggerReminderComponent
            .builder()
            .appComponent(PorosenokPetrApp.component!!)
            .build()
        component.inject(this)

        //todo
    }
}