package space.rodionov.porosenokpetr.core.data.remote.space.rodionov.porosenokpetr.feature_reminder.domain.use_case

import com.google.common.truth.Truth.assertThat
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.junit.Test
import org.junit.jupiter.api.Assertions

class CollectReminderTimeUseCaseTest {

    //todo coroutines for test
    @Test
    fun shouldReturnLocalTime_WhenRelevantFormat() {
        val timeString = "11:20"
        Assertions.assertDoesNotThrow {
            val result = stringToLocalTime(timeString)
            assertThat(result).isEqualTo(
                LocalTime.parse(
                    timeString,
                    DateTimeFormat.forPattern("HH:mm")
                )
            )
        }
    }

    @Test
    fun shouldThrowException_WhenWrongFormat() {
        val timeString = ""
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            stringToLocalTime(timeString)
        }
        assertThat(exception.message).isEqualTo("Couldn't parse string \"$timeString\" into LocalTime format")
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