package space.rodionov.porosenokpetr.core

import android.util.Log
import space.rodionov.porosenokpetr.feature_driller.utils.Constants
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_NOTIFY
import java.text.SimpleDateFormat
import java.util.*

val sdf = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

fun parseLongToHoursAndMinutes(millis: Long) : Pair<String, String> {
    val hours = millis / Constants.MILLIS_IN_ONE_HOUR
    val rest = millis % Constants.MILLIS_IN_ONE_HOUR
    val minutes = rest / Constants.MILLIS_IN_ONE_MINUTE
    Log.d(TAG_NOTIFY, "parseLongToHoursAndMinutes: hours = $hours, minutes = $minutes")
    return Pair(hours.addZeroToNumber(), minutes.addZeroToNumber())
}

private fun Long.addZeroToNumber() : String {
    return if (this.toString().length < 2) "0$this" else this.toString()
}

private fun Int.addZeroToNumber() : String {
    return if (this.toString().length < 2) "0$this" else this.toString()
}

fun String.roundMinutesToFiveMinutes() : String {
    var number = 0
    try {
        number = this.toInt()
        println(number)
    } catch (e: Exception) {
        println(e.message)
    }
    while (number % 5 != 0) {
        number--
    }
    return number.addZeroToNumber()
}

fun hoursAndMinutesToMillis(hours: String, minutes: String) : Long {
    val hoursInMillis = hours.toInt() * Constants.MILLIS_IN_ONE_HOUR
    val minutesInMillis = minutes.toInt() * Constants.MILLIS_IN_ONE_MINUTE
    val millis = hoursInMillis + minutesInMillis
    return millis
}

fun findUpcomingNotificationTime(millisSinceDayStart: Long): Long {
    val curTimeStamp: Long = System.currentTimeMillis() // today current millis
    val todayString = sdf.format(curTimeStamp)
    val todayStartTimeStamp = Calendar.getInstance().apply { this.time = sdf.parse(todayString) }.timeInMillis // today start
    val todayPlusMillisSinceDayStart = todayStartTimeStamp + millisSinceDayStart // today + notifyTimeMillis
    return if (curTimeStamp < todayPlusMillisSinceDayStart) {
        logNotificationTime(todayPlusMillisSinceDayStart)
        todayPlusMillisSinceDayStart
    } else {
        tomorrowPlusMillisSinceDayStart(millisSinceDayStart)
    }
}

private fun tomorrowPlusMillisSinceDayStart(millisSinceDayStart: Long): Long {
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, 1)
    val tomorrowString = sdf.format(cal.timeInMillis)
    val tomorrowStartTimeStamp = Calendar.getInstance()
        .apply { this.time = sdf.parse(tomorrowString) }.timeInMillis // tomorrow start
    val tomorrowPlusMillisSinceDayStart = tomorrowStartTimeStamp + millisSinceDayStart // tomorrow + notifyTimeMillis
    logNotificationTime(tomorrowPlusMillisSinceDayStart)
    return tomorrowPlusMillisSinceDayStart
}

private fun logNotificationTime(timestamp: Long) {
    val cal = Calendar.getInstance()
    cal.timeInMillis = timestamp
    val date = cal.time
    Log.d(Constants.TAG_PETR, "logNotificationTime: $date")
}

fun getHours() = arrayOf(
    "00",
    "01",
    "02",
    "03",
    "04",
    "05",
    "06",
    "07",
    "08",
    "09",
    "10",
    "11",
    "12",
    "13",
    "14",
    "15",
    "16",
    "17",
    "18",
    "19",
    "20",
    "21",
    "22",
    "23"
)
fun getMinutes() = arrayOf(
    "00",
    "05",
    "10",
    "15",
    "20",
    "25",
    "30",
    "35",
    "40",
    "45",
    "50",
    "55"
)