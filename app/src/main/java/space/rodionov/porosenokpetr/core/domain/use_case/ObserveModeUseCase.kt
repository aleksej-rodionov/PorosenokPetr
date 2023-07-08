package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage

class ObserveModeUseCase(
    private val repo: KeyValueStorage
) {

    operator fun invoke() = repo.modeFlow()
}