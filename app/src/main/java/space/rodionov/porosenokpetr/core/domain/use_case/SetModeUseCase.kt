package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class SetModeUseCase(
    private val repo: Preferences
) {

   suspend operator fun invoke(mode: Int) = repo.updateMode(mode)
}