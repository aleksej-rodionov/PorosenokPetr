package space.rodionov.porosenokpetr.feature_reminder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.UpdateReminderTimeUseCase.Companion.REMINDER_TIME_KEY


class CollectReminderTimeUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke(): Flow<LocalTime> {
        return keyValueStorage.collectValue(
            REMINDER_TIME_KEY,
            ""
        ).map {
            stringToLocalTime(it)
        }
    }

    private fun stringToLocalTime(timeString: String): LocalTime {
        return try {
            val formatter = DateTimeFormat.forPattern("HH:mm")
            LocalTime.parse(timeString, formatter)
        } catch (e: Exception) {
            throw RuntimeException("Couldn't parse string \"$timeString\" into LocalTime format")
        }
    }

//    private fun stringToClosestUpcumingLocalDateTime(timeString: String): LocalDateTime {
//        val formatter = DateTimeFormat.forPattern("hh:mm a")
//        val time = LocalTime.parse(timeString, formatter)
//        val now = LocalDateTime.now()
//        val todayDateTime = now.withTime(time.hourOfDay, time.minuteOfHour, 0, 0)
//        val tomorrowDateTime = todayDateTime.plusDays(1)
//
//        return if (now.isBefore(todayDateTime)) {
//            todayDateTime
//        } else {
//            tomorrowDateTime
//        }
//    }//todo use
}