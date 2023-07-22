package space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.UpdateInterfaceLanguageUseCase.Companion.INTERFACE_LANGUAGE_KEY
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.core.util.Language.Companion.LANGUAGE_RU
import space.rodionov.porosenokpetr.core.util.Language.Companion.resolveLanguage

class CollectInterfaceLanguageUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke(): Flow<Language> = keyValueStorage.collectValue(
        INTERFACE_LANGUAGE_KEY,
        LANGUAGE_RU
    ).map {
        resolveLanguage(it)
    }
}