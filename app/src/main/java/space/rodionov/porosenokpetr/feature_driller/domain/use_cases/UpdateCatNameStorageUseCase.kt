package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class UpdateCatNameStorageUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(catName: String) = repo.updateStorageCat(catName)
}