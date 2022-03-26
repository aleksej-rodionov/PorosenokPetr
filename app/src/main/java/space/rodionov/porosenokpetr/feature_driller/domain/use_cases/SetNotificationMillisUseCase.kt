package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class SetNotificationMillisUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(millis: Long) = repo.setNotifyMillis(millis)
}