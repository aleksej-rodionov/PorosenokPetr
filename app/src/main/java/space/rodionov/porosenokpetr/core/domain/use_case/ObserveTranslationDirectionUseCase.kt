package space.rodionov.porosenokpetr.core.domain.use_case

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage

class ObserveTranslationDirectionUseCase(
    private val repo: KeyValueStorage
) {

    operator fun invoke(): Flow<Boolean> = repo.translationDirectionFlow()
}