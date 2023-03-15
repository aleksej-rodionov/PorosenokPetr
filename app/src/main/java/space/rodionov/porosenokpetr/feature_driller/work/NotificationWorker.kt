package space.rodionov.porosenokpetr.feature_driller.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.success
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import space.rodionov.porosenokpetr.core.MainActivity
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.util.vectorToBitmap
import space.rodionov.porosenokpetr.core.util.Constants

class NotificationWorker @AssistedInject constructor (
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val id = inputData.getLong(NOTIFICATION_ID, 0).toInt()
        val millisSinceDayStart = inputData.getLong(MILLIS_SINCE_DAY_START, Constants.MILLIS_IN_NINE_HOURS)
        sendNotification(id, millisSinceDayStart)

        return success()
    }

    private fun sendNotification(id: Int, millisSinceDayStart: Long) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NOTIFICATION_ID, id)

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val bitmap = applicationContext.vectorToBitmap(R.drawable.ic_clock_black)
        val titleNotification = applicationContext.getString(R.string.notification_title)
        val subtitleNotification = applicationContext.getString(R.string.notification_subtitle)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setLargeIcon(bitmap)
            .setSmallIcon(R.drawable.ic_clock_white)
            .setContentTitle(titleNotification)
            .setContentText(subtitleNotification)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent) // направляет по тапу на MainActivity, где startDest is DrillerFragment
            .setAutoCancel(true)

        notification.priority = NotificationCompat.PRIORITY_MAX

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setChannelId(NOTIFICATION_CHANNEL)

            val ringtoneManager = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL,
                NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            channel.setSound(ringtoneManager, audioAttributes)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notification.build())

        notificationHelper.buildNotification(millisSinceDayStart)
    }



    companion object {
        const val NOTIFICATION_ID = "porosenok_petr_notification_id"
        const val NOTIFICATION_NAME = "porosenok_petr"
        const val NOTIFICATION_CHANNEL = "porosenok_petr_channel_01"
        const val NOTIFICATION_WORK = "porosenok_petr_notification_work"
        const val MILLIS_SINCE_DAY_START = "millis_since_day_start"
    }

    /**
     * class annotate with @AssistedFactory will available in the dependency graph, you don't need
     * additional binding from [HelloWorldWorker_Factory_Impl] to [Factory].
     */
    @AssistedFactory
    interface Factory{
        fun create(context: Context, params: WorkerParameters): NotificationWorker
    }
}