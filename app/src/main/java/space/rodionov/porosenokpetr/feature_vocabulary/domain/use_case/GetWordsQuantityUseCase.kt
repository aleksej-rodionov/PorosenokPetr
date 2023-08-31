package space.rodionov.porosenokpetr.feature_vocabulary.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class GetWordsQuantityUseCase(
    private val repository: WordRepo
) {

    suspend operator fun invoke(): Int {
        return repository.getWordsQuantity()
    }
}