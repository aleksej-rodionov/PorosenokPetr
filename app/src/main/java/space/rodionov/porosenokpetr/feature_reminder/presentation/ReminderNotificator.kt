package space.rodionov.porosenokpetr.feature_reminder.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.feature_launcher.presentation.LauncherActivity

/**
 * Просто показывалка нотификации, выносим из ресивера чтобы освободить место
 */
class ReminderNotificator(
    private val context: Context
) {

    fun showNotification() {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            REMINDER_NONIFICATION_CHANNEL_ID,
            "Reminder Notification Channel",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(1, buildNotification())
    }

    private fun buildNotification(): Notification {
        val activityIntent = Intent(context, LauncherActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, REMINDER_NONIFICATION_CHANNEL_ID)
            .setContentTitle("Ty ne pozanimalsä segodnä 10 minut")
            .setContentText("Ty profilonil, suka")
            .setSmallIcon(R.drawable.ic_bookstack)
            .setContentIntent(activityPendingIntent)
            .setAutoCancel(true)
            .build() //todo how to make it vibrate?
    }

    companion object {
        private const val REMINDER_NONIFICATION_CHANNEL_ID = "reminder_nonification_channel_id"
    }
}