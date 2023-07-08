package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_RU

class CollectNativeLanguageUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke() = keyValueStorage.collectValue(NATIVE_LANGUAGE_KEY, LANGUAGE_RU)

    companion object {
        const val NATIVE_LANGUAGE_KEY = "nativeLanguage"
    }
}