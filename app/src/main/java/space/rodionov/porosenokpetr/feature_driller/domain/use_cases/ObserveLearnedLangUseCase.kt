package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class ObserveLearnedLangUseCase(
    private val repo: WordRepo
) {

    operator fun invoke() = repo.observeLearnedLanguage()
}