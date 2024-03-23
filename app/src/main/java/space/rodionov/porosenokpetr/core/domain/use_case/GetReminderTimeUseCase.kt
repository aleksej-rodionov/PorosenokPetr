package space.rodionov.porosenokpetr.core.domain.use_case

import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.UpdateReminderTimeUseCase.Companion.REMINDER_TIME_KEY

class GetReminderTimeUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke(): LocalTime {
//        return stringToLocalTime(keyValueStorage.getValue(REMINDER_TIME_KEY, ""))//todo vernutj
        return stringToLocalTime(keyValueStorage.getValue(REMINDER_TIME_KEY, "20:00"))
    }

    private fun stringToLocalTime(timeString: String): LocalTime {
        return try {
            val formatter = DateTimeFormat.forPattern("HH:mm")
            LocalTime.parse(timeString, formatter)
        } catch (e: Exception) {
            throw RuntimeException("Couldn't parse string \"$timeString\" into LocalTime format")
        }
    }
}