package space.rodionov.porosenokpetr.core.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import org.joda.time.Duration
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import space.rodionov.porosenokpetr.core.domain.repository.ReminderAlarmManager
import space.rodionov.porosenokpetr.feature_reminder.presentation.AlarmReceiver

private const val TAG = "ReminderRepositoryImplTAGGY"

class ReminderAlarmManagerImpl(
    private val context: Context
) : ReminderAlarmManager {

    override fun enable(time: LocalTime) {
        val closestAlarmTimestamp = findClosestAlarmTime(time)
        val gapInMillis = findGapInMillis(time)
        val alarmPendingIntent = buildAlarmPendingIntent()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        Log.d(TAG, "enable: closestAlarmTimestamp = $closestAlarmTimestamp")
        Log.d(TAG, "enable: gapInMillis = $gapInMillis")
//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, gapInMillis, alarmPendingIntent)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, closestAlarmTimestamp, alarmPendingIntent)
    }

    override fun disable() {
        val alarmPendingIntent = buildAlarmPendingIntent()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        Log.d(TAG, "disable: called")
        alarmManager.cancel(alarmPendingIntent)
    }

    override fun check(): Boolean {
        return isAlarmSet()
    }

    /**
     * PendingIntent Аларма, который выстреливается при срабатывании Аларма.
     * Должен ловиться AlarmReceiver-ом.
     */
    private fun buildAlarmPendingIntent(isForChecking: Boolean = false): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = REMINDER_ALARM_ACTION
        val intentFlags = if (isForChecking) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        }
        return PendingIntent.getBroadcast(
            context,
            REMINDER_ALARM_REQUEST_CODE,
            intent,
            intentFlags
        )
    }

    /**
     * Находит кол-во миллисекудн до момента, когда должен сработать Аларм
     */
    private fun findClosestAlarmTime(time: LocalTime): Long {
        val now = LocalDateTime.now()
        val closestAlarmTime = findClosestAlarmTime(time, now)
        val dateTime = closestAlarmTime.toDateTime()
        return dateTime.millis
    }

    /**
     * Находит кол-во миллисекудн до момента, когда должен сработать Аларм
     */
    private fun findGapInMillis(time: LocalTime): Long {
        val now = LocalDateTime.now()
        val closestAlarmTime = findClosestAlarmTime(time, now)
        return Duration(now.toDateTime(), closestAlarmTime.toDateTime()).millis
    }

    /**
     * Находит время, на которое завести Аларм
     */
    private fun findClosestAlarmTime(localTime: LocalTime, now: LocalDateTime): LocalDateTime {
        val time: LocalDateTime = LocalDate.now().toLocalDateTime(localTime)
        return if (now.isBefore(time)) {
            time
        } else {
            time.plusDays(1)
        }
    }

    /**
     * Проверяет заведен ли уже Аларм
     */
    fun isAlarmSet(): Boolean {
//        val alarmPendingIntent = buildAlarmPendingIntent(true)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val nextAlarm = alarmManager.nextAlarmClock
        Log.d(TAG, "isAlarmSet: alarmManager.nextAlarmClock = ${nextAlarm.toString()}")
        return nextAlarm != null && nextAlarm.triggerTime != 1992L
    }

    companion object {
        const val REMINDER_ALARM_REQUEST_CODE = 1991
        const val REMINDER_ALARM_ACTION = "reminder_alarm_action"
    }
}