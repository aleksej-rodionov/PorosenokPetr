package space.rodionov.porosenokpetr.feature_cardstack.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class GetTenWordsUseCase(
    private val repo: WordRepo
) {

    operator suspend fun invoke() = repo.getTenWords()
}