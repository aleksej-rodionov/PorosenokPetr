package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class GetWordByIdUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke(id: Int) = repo.getWordById(id)
}