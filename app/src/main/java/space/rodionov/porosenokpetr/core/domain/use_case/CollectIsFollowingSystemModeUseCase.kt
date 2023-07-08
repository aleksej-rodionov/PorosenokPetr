package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage

class CollectIsFollowingSystemModeUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke() = keyValueStorage.collectValue(IS_FOLLOWING_SYSTEM_MODE_KEY, false)

    companion object {
        const val IS_FOLLOWING_SYSTEM_MODE_KEY = "isFollowingSystemMode"
    }
}