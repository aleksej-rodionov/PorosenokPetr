package space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.CollectNativeLanguageUseCase.Companion.NATIVE_LANGUAGE_KEY

class UpdateNativeLangUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    suspend operator fun invoke(language: Int) = keyValueStorage.updateValue(
        NATIVE_LANGUAGE_KEY,
        language
    )
}