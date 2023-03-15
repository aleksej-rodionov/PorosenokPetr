package space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.Preferences

class UpdateNativeLangUseCase(
    private val repo: Preferences
) {

    suspend operator fun invoke(newLang: Int) = repo.updateNativeLanguage(newLang)
}