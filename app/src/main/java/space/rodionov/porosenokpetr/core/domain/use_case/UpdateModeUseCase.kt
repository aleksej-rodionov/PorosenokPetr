package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.domain.use_case.CollectModeUseCase.Companion.MODE_KEY

class UpdateModeUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    suspend operator fun invoke(mode: Int) = keyValueStorage.updateValue(
        MODE_KEY,
        mode
    )
}