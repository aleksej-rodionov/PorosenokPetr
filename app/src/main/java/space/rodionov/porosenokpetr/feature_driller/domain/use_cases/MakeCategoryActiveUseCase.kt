package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class MakeCategoryActiveUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(name: String, makeActive: Boolean) = repo.makeCategoryActive(name, makeActive)
}