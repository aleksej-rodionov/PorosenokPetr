package space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class ObserveAllActiveCatsNamesUseCase(
    private val repo: WordRepo
) {

    operator fun invoke() = repo.observeAllActiveCatsNames()
}