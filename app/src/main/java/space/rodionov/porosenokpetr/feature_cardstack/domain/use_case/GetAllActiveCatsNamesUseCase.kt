package space.rodionov.porosenokpetr.feature_cardstack.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class GetAllActiveCatsNamesUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(): List<String> {
        return repo.getAllActiveCatsNames()
    }
}