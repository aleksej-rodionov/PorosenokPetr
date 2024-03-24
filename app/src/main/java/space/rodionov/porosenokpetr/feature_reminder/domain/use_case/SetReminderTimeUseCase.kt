package space.rodionov.porosenokpetr.feature_reminder.domain.use_case

import org.joda.time.LocalTime
import space.rodionov.porosenokpetr.core.domain.common.LocalTimeFormatter
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage


class SetReminderTimeUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke(hourOfDay: Int, minuteOfHour: Int) {
        keyValueStorage.setValue(
            REMINDER_TIME_KEY,
            LocalTimeFormatter.serializeToString(LocalTime(hourOfDay, minuteOfHour))
        )
    }

    companion object {
        const val REMINDER_TIME_KEY = "reminder_time_key"
    }
}