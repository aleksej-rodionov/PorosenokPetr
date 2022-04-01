package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class ObserveNativeLangUseCase(
    private val repo: WordRepo
) {

    operator fun invoke() = repo.observeNativeLanguage()
}