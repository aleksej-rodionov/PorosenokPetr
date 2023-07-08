package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.BuildConfig
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_EN
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_SE

class CollectLearnedLanguageUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke() = keyValueStorage.collectValue(
        LEARNED_LANGUAGE_KEY,
        if (BuildConfig.FLAVOR == "swedishdriller") LANGUAGE_SE else LANGUAGE_EN
    )

    companion object {
        const val LEARNED_LANGUAGE_KEY = "learnedLanguage"
    }
}