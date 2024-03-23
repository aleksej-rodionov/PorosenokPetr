package space.rodionov.porosenokpetr.feature_reminder.domain.use_case

import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage


class UpdateReminderTimeUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    suspend operator fun invoke(hourOfDay: Int, minuteOfHour: Int) {
        keyValueStorage.updateValue(
            REMINDER_TIME_KEY,
            convertTimeToString(hourOfDay, minuteOfHour)
        )
    }

    private fun convertTimeToString(hourOfDay: Int, minuteOfHour: Int): String {
        val localTime = LocalTime(hourOfDay, minuteOfHour)
        val formatter = DateTimeFormat.forPattern("HH:mm")
        return formatter.print(localTime)
    }

    companion object {
        const val REMINDER_TIME_KEY = "reminder_time_key"
    }
}