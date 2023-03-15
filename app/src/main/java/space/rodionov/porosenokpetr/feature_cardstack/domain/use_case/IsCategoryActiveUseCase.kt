package space.rodionov.porosenokpetr.feature_cardstack.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class IsCategoryActiveUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(name: String): Boolean = repo.isCatActive(name)
}