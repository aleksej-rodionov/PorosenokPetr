package space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage

class UpdateLearnedLangUseCase(
    private val repo: KeyValueStorage
) {

    suspend operator fun invoke(newLang: Int) = repo.updateLearnedLanguage(newLang)
}