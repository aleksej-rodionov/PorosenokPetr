package space.rodionov.porosenokpetr.core.domain.repository

import org.joda.time.LocalTime

interface ReminderAlarmManager {

    fun enable(time: LocalTime)

    fun disable()
}