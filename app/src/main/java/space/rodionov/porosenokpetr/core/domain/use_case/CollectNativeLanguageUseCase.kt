package space.rodionov.porosenokpetr.core.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.core.util.Language.Companion.LANGUAGE_RU
import space.rodionov.porosenokpetr.core.util.Language.Companion.resolveLanguage

class CollectNativeLanguageUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke(): Flow<Language> = keyValueStorage.collectValue(
        NATIVE_LANGUAGE_KEY,
        LANGUAGE_RU
    ).map {
        resolveLanguage(it)
    }

    companion object {
        const val NATIVE_LANGUAGE_KEY = "nativeLanguage"
    }
}