package space.rodionov.porosenokpetr.core.domain.use_case

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage

class CollectTranslationDirectionUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke(): Flow<Boolean> = keyValueStorage.collectValue(
        IS_NATIVE_TO_FOREIGN_KEY,
        false
    )

    companion object {
        const val IS_NATIVE_TO_FOREIGN_KEY = "isNativeToForeign"
    }
}