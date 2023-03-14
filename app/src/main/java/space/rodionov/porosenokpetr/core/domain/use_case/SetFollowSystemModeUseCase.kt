package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class SetFollowSystemModeUseCase(
    private val repo: Preferences
) {

   suspend operator fun invoke(follow: Boolean) = repo.updateFollowSystemMode(follow)
}