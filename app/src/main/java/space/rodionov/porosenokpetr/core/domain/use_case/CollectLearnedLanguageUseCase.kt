package space.rodionov.porosenokpetr.core.domain.use_case

import kotlinx.coroutines.flow.map
import space.rodionov.porosenokpetr.BuildConfig
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.util.Language.Companion.LANGUAGE_EN
import space.rodionov.porosenokpetr.core.util.Language.Companion.LANGUAGE_SE
import space.rodionov.porosenokpetr.core.util.Language.Companion.resolveLanguage

class CollectLearnedLanguageUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke() = keyValueStorage.collectValue(
        LEARNED_LANGUAGE_KEY,
        if (BuildConfig.FLAVOR == "swedishdriller") {
            LANGUAGE_SE
        } else {
            LANGUAGE_EN
        }
    ).map {
        resolveLanguage(it)
    }

    companion object {
        const val LEARNED_LANGUAGE_KEY = "learnedLanguage"
    }
}