package space.rodionov.porosenokpetr.core.data.remote.space.rodionov.porosenokpetr.feature_reminder.domain.use_case

import com.google.common.truth.Truth.assertThat
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.junit.Test
import org.junit.jupiter.api.Assertions

class UpdateReminderTimeUseCaseTest {

    //todo coroutines for test
    @Test
    fun shouldFormatTimeToStringCorrectly() {
        val hour = 17
        val minute = 20
        Assertions.assertDoesNotThrow {
            val result = convertTimeToString(hour, minute)
            assertThat(result).isEqualTo("17:20")
        }
    }

    private fun convertTimeToString(hourOfDay: Int, minuteOfHour: Int): String {
        val localTime = LocalTime(hourOfDay, minuteOfHour)
        val formatter = DateTimeFormat.forPattern("HH:mm")
        return formatter.print(localTime)
    }
}