package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.Preferences

class SetModeUseCase(
    private val repo: Preferences
) {

   suspend operator fun invoke(mode: Int) = repo.updateMode(mode)
}