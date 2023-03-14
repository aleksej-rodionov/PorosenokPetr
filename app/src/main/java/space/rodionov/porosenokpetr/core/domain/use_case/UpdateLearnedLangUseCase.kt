package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.Preferences

class UpdateLearnedLangUseCase(
    private val repo: Preferences
) {

    suspend operator fun invoke(newLang: Int) = repo.updateLearnedLanguage(newLang)
}