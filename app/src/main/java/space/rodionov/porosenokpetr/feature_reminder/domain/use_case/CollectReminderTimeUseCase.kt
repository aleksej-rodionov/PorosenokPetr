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
}