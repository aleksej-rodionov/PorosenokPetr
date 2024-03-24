package space.rodionov.porosenokpetr.core.domain.use_case

import org.joda.time.LocalTime
import space.rodionov.porosenokpetr.core.domain.common.LocalTimeFormatter
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.feature_reminder.domain.use_case.UpdateReminderTimeUseCase.Companion.REMINDER_TIME_KEY

class GetReminderTimeUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke(): LocalTime {
        return LocalTimeFormatter.parseFromString(keyValueStorage.getValue(REMINDER_TIME_KEY, DEFAULT_REIMNDER_TIME_STRING))
    }

    companion object {
        const val DEFAULT_REIMNDER_TIME_STRING = "20:00"
    }
}