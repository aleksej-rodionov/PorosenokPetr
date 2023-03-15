package space.rodionov.porosenokpetr.core.domain.use_case

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.core.domain.preferences.Preferences

class ObserveTranslationDirectionUseCase(
    private val repo: Preferences
) {

    operator fun invoke(): Flow<Boolean> = repo.translationDirectionFlow()
}