package space.rodionov.porosenokpetr.feature_settings.domain.use_case.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.CollectIsFollowingSystemModeUseCase.Companion.IS_FOLLOWING_SYSTEM_MODE_KEY

class UpdateIsFollowingSystemModeUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    suspend operator fun invoke(following: Boolean) =
        keyValueStorage.updateValue(IS_FOLLOWING_SYSTEM_MODE_KEY, following)
}