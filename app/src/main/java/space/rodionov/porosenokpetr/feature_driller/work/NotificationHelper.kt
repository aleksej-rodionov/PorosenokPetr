package space.rodionov.porosenokpetr.feature_driller.work

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.findUpcomingNotificationTime
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class NotificationHelper(
   private val context: Context
) {

    fun buildNotification(): Long? {
        val notificationTime = findUpcomingNotificationTime() // notification timestamp
        val currentTime = System.currentTimeMillis() // current timestamp

        if (notificationTime > currentTime) {
            val data = Data.Builder().putInt(NotificationWorker.NOTIFICATION_ID, 0).build()
            val delay = notificationTime - currentTime
//            val delay = ONE_MIN_IN_MILLIS
            scheduleNotification(delay, data)

            logNotificationScheduled(notificationTime)
            return notificationTime
        } else {
            logIncorrectTimeError()
            return null
        }
    }

    private fun scheduleNotification(delay: Long, data: Data) {
        val notificationWork = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        val instanceWorkManager = WorkManager.getInstance(context) // хз, тот ли контекст сюда запустил?
        instanceWorkManager.beginUniqueWork(
            NotificationWorker.NOTIFICATION_WORK,
            ExistingWorkPolicy.REPLACE,
            notificationWork
        ).enqueue()
    }

    fun cancelNotification() {
        val instanceWorkManager = WorkManager.getInstance(context)
        Log.d(Constants.TAG_NOTIFY, "cancelNotification: instanceWorkManager.hashcode = ${instanceWorkManager.hashCode()}")
        instanceWorkManager.cancelUniqueWork(NotificationWorker.NOTIFICATION_WORK)
    }

    private fun logNotificationScheduled(notificationTime: Long) {
        val titleNotificationSchedule = context.resources.getString(R.string.notification_schedule_title)
        val patternNotificationSchedule = context.resources.getString(R.string.notification_schedule_pattern)
        Log.d(
            Constants.TAG_NOTIFY, "buildNotification: ${titleNotificationSchedule + SimpleDateFormat(
            patternNotificationSchedule, Locale.getDefault()
        ).format(notificationTime).toString()}")
    }

    private fun logIncorrectTimeError() {
        val errorNotificationSchedule = context.resources.getString(R.string.notification_schedule_error)
        Log.d(Constants.TAG_NOTIFY, "buildNotification: $errorNotificationSchedule")
    }
}