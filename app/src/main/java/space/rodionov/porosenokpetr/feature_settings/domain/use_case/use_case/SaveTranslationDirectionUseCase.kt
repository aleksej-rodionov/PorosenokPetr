package space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.Preferences

class SaveTranslationDirectionUseCase(
    private val repo: Preferences
) {

    suspend operator fun invoke(nativeToForeign: Boolean) = repo.updatetranslationDirection(nativeToForeign)
}