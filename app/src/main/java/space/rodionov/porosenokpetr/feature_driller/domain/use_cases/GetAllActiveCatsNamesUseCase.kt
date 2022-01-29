package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class GetAllActiveCatsNamesUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(): List<String> {
        return repo.getAllActiveCatsNames()
    }
}