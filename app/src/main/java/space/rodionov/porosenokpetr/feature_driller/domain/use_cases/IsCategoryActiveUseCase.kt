package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class IsCategoryActiveUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(name: String): Boolean = repo.isCatActive(name)
}