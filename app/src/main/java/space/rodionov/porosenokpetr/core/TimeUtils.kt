package space.rodionov.porosenokpetr.core

import android.util.Log
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

val sdf = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

fun findUpcomingNotificationTime(): Long {
    val curTimeStamp: Long = System.currentTimeMillis() // today current millis
    val todayString = sdf.format(curTimeStamp)
    val todayStartTimeStamp = Calendar.getInstance().apply { this.time = sdf.parse(todayString) }.timeInMillis // today start
    val todayPlusTwentyOneTimeStamp = todayStartTimeStamp + 3600000 * 21 // today 21:00
    return if (curTimeStamp < todayPlusTwentyOneTimeStamp) {
        logNotificationTime(todayPlusTwentyOneTimeStamp)
        todayPlusTwentyOneTimeStamp
    } else {
        tomorrowPlusTwentyOneTimeStamp()
    }
}

private fun tomorrowPlusTwentyOneTimeStamp(): Long {
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, 1)
    val tomorrowString = sdf.format(cal.timeInMillis)
    val tomorrowStartTimeStamp = Calendar.getInstance()
        .apply { this.time = sdf.parse(tomorrowString) }.timeInMillis // tomorrow start
    val tomorrowPlusTwentyOneTimeStamp = tomorrowStartTimeStamp + 3600000 * 21 + 2700000 // tomorrow 21:00
    logNotificationTime(tomorrowPlusTwentyOneTimeStamp)
    return tomorrowPlusTwentyOneTimeStamp
}

private fun logNotificationTime(timestamp: Long) {
    val cal = Calendar.getInstance()
    cal.timeInMillis = timestamp
    val date = cal.time
    Log.d(Constants.TAG_PETR, "logNotificationTime: $date")
}