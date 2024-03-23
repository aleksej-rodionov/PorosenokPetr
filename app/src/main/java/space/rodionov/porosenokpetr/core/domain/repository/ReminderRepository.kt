package space.rodionov.porosenokpetr.core.domain.repository

import org.joda.time.LocalTime

interface ReminderRepository {

    fun enable(time: LocalTime)

    fun disable()

    fun check(): Boolean
}