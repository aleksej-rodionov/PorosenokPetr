package space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.CollectNativeLanguageUseCase.Companion.NATIVE_LANGUAGE_KEY
import space.rodionov.porosenokpetr.core.util.Language

class UpdateNativeLanguageUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    suspend operator fun invoke(language: Language) = keyValueStorage.updateValue(
        NATIVE_LANGUAGE_KEY,
        language.languageCode
    )
}