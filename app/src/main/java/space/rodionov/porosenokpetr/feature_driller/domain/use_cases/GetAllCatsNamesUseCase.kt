package space.rodionov.porosenokpetr.feature_driller.domain.use_cases

import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class GetAllCatsNamesUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(): List<String> {
        return repo.getAllCatsNames()
    }
}