package space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.Preferences

class SetFollowSystemModeUseCase(
    private val repo: Preferences
) {

   suspend operator fun invoke(follow: Boolean) = repo.updateFollowSystemMode(follow)
}