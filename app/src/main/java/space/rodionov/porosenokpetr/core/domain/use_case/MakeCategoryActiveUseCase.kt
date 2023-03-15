package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class MakeCategoryActiveUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(name: String, makeActive: Boolean) = repo.makeCategoryActive(name, makeActive)
}