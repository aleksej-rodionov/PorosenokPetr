package space.rodionov.porosenokpetr.core.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.feature_launcher.domain.use_case.SetAvailableNativeLanguagesUseCase.Companion.AVAILABLE_NATIVE_LANGUAGES_KEY

class CollectAvailableNativeLanguagesUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke(): Flow<List<Language>> {
        return keyValueStorage.collectListValue(
            AVAILABLE_NATIVE_LANGUAGES_KEY,
            emptyList<String>()
        ).map { stringList ->
            stringList.map {
                Language.resolveLanguage(it)
            }
        }
    }
}