package space.rodionov.porosenokpetr.core.domain.common

class UseCaseException(
    private val useCaseExceptionMessage: String
): Exception(useCaseExceptionMessage)