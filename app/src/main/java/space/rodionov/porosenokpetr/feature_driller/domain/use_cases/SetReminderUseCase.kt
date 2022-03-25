package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class SetReminderUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(remind: Boolean) {
        repo.setRemind(remind)
    }
}