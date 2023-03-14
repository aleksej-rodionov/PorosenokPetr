package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class ObserveModeUseCase(
    private val repo: Preferences
) {

    operator fun invoke() = repo.modeFlow()
}