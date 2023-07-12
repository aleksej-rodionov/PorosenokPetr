package space.rodionov.porosenokpetr.feature_launcher.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.CollectLearnedLanguageUseCase.Companion.LEARNED_LANGUAGE_KEY
import space.rodionov.porosenokpetr.core.util.Language

class SetLearnedLanguageUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    suspend operator fun invoke(language: Language) = keyValueStorage.updateValue(
        LEARNED_LANGUAGE_KEY,
        language.languageCode
    )
}