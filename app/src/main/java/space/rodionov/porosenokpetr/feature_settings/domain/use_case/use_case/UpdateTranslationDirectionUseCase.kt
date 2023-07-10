package space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.CollectTranslationDirectionUseCase.Companion.IS_NATIVE_TO_FOREIGN_KEY

class UpdateTranslationDirectionUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    suspend operator fun invoke(isNativeToForeign: Boolean) =
        keyValueStorage.updateValue(IS_NATIVE_TO_FOREIGN_KEY, isNativeToForeign)
}