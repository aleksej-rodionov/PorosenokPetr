package space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage

class SetFollowSystemModeUseCase(
    private val repo: KeyValueStorage
) {

   suspend operator fun invoke(follow: Boolean) = repo.updateFollowSystemMode(follow)
}