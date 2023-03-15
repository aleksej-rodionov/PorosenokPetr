package space.rodionov.porosenokpetr.feature_cardstack.domain.use_case

import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class GetRandomWordUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(activeCatsNames: List<String>) : Word {
        return repo.getRandomWordFromActiveCats(activeCatsNames)
    }
}