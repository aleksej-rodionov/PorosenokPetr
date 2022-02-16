package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class SaveTranslationDirectionUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(nativeToForeign: Boolean) = repo.setTransDir(nativeToForeign)
}