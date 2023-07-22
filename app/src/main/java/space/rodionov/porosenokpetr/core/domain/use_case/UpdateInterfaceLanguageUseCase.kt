package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage

class UpdateInterfaceLanguageUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    suspend operator fun invoke(languageTag: String) = keyValueStorage.updateValue(
        INTERFACE_LANGUAGE_KEY,
        languageTag
    )

    companion object {
        const val INTERFACE_LANGUAGE_KEY = "interfaceLanguage"
    }
}