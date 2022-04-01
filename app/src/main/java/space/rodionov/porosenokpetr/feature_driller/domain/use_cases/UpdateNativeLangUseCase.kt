package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class UpdateNativeLangUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(newLang: Int) = repo.updateNativeLanguage(newLang)
}