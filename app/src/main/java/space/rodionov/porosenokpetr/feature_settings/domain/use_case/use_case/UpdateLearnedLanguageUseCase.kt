package space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.CollectLearnedLanguageUseCase.Companion.LEARNED_LANGUAGE_KEY

class UpdateLearnedLanguageUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    suspend operator fun invoke(language: Int) = keyValueStorage.updateValue(
        LEARNED_LANGUAGE_KEY,
        language
    )
}