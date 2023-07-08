package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT

class CollectModeUseCase(
    private val keyValueStorage: KeyValueStorage
) {

    operator fun invoke() = keyValueStorage.collectValue(MODE_KEY, MODE_LIGHT)

    companion object {
        const val MODE_KEY = "mode"
    }
}