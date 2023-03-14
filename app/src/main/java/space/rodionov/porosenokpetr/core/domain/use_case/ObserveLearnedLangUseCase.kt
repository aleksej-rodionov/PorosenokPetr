package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.Preferences

class ObserveLearnedLangUseCase(
    private val repo: Preferences
) {

    operator fun invoke() = repo.learnedLanguageFlow()
}