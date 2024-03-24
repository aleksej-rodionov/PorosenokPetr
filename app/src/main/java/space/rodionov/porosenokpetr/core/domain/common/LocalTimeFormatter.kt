package space.rodionov.porosenokpetr.core.domain.common

import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat

object LocalTimeFormatter {

    private const val PATTERN = "HH:mm"
    private val formatter = DateTimeFormat.forPattern(PATTERN)

    fun serializeToString(time: LocalTime): String {
        return formatter.print(time)
    }

    fun parseFromString(timeString: String): LocalTime {
        return try {
            LocalTime.parse(timeString, formatter)
        } catch (e: Exception) {
            throw FormatterException("Couldn't parse string \"$timeString\" into LocalTime format")
        }
    }

    class FormatterException(formatterExceptionMessage: String) : Exception(formatterExceptionMessage)
}