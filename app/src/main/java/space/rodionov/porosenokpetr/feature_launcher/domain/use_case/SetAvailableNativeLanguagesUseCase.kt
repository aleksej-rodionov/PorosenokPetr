package space.rodionov.porosenokpetr.feature_launcher.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.util.Language

class SetAvailableNativeLanguagesUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    suspend operator fun invoke(languages: List<Language>) {
        val languageCodes = languages.map { it.languageCode }
        keyValueStorage.updateListValue(AVAILABLE_NATIVE_LANGUAGES_KEY, languageCodes)
    }

    companion object {
        const val AVAILABLE_NATIVE_LANGUAGES_KEY = "availableNativeLanguages"
    }
}