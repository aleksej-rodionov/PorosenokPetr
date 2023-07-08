package space.rodionov.porosenokpetr.feature_cardstack.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class GetTenWordsUseCase(
    private val repo: WordRepo
) {

    suspend operator fun invoke() = repo.getTenWords()
}