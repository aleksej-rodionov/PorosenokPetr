package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage

class ObserveFollowSystemModeUseCase(
    private val repo: KeyValueStorage
) {

    operator fun invoke() = repo.followSystemModeFlow()
}