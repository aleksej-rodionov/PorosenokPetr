package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.util.Language

class GetLearnedLanguageUseCase {
    operator fun invoke() = Language.resolveLanguage(LANGUAGE_SE)
}