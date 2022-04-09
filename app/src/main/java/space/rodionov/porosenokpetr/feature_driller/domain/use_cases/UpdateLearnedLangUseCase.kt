package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class UpdateLearnedLangUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(newLang: Int) = repo.updateLearnedLanguage(newLang)
}