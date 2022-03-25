package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class ObserveReminderUseCase(
    private val repo: WordRepo
) {

    operator fun invoke(): Flow<Boolean> = repo.getRemind()
}