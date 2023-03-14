package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.Preferences

class ObserveNativeLangUseCase(
    private val repo: Preferences
) {

    operator fun invoke() = repo.nativeLanguageFlow()
}