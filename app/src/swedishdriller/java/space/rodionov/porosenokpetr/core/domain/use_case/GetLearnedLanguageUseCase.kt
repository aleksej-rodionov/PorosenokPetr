package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.core.util.Language.Companion.LANGUAGE_SE

class GetLearnedLanguageUseCase {
    operator fun invoke() = Language.resolveLanguage(LANGUAGE_SE)
}