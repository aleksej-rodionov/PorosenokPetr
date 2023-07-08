package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage

class SetModeUseCase(
    private val repo: KeyValueStorage
) {

   suspend operator fun invoke(mode: Int) = repo.updateMode(mode)
}