package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class SetModeUseCase(
    private val repo: WordRepo
) {

   suspend operator fun invoke(mode: Int) = repo.setMode(mode)
}